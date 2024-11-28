package com.pvz.datadots.presentation.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.pvz.datadots.databinding.FragmentResultsBinding
import com.pvz.datadots.domain.model.Point
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultsFragment : Fragment() {

    private val viewModel: ResultsViewModel by viewModels()

    private val binding by lazy {
        FragmentResultsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.fetchPointsData()
        viewModel.pointList.observe(viewLifecycleOwner) { points ->
            if (points.isEmpty()) {
                Toast.makeText(requireContext(), "Нет данных для отображения", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            with(binding.pointsRecyclerView) {
                layoutManager = LinearLayoutManager(context)
                adapter = PointsAdapter(points)
            }

            setupChart(points)
        }

        return binding.root
    }

    private fun setupChart(points: List<Point>) {
        val entries = points.sortedBy { it.x }.map { Entry(it.x, it.y) }

        val dataSet = LineDataSet(entries, "График точек").apply {
            val theme = requireContext().theme
            color = resources.getColor(android.R.color.holo_blue_dark, theme)
            valueTextColor = resources.getColor(android.R.color.black, theme)
            setDrawCircles(true)
            setDrawCircleHole(false)
            lineWidth = 2f
            circleRadius = 5f
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }

        with(binding.lineChart){
            data = LineData(dataSet)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
            }
            axisLeft.isEnabled = true
            axisRight.isEnabled = false

            description.isEnabled = false
            invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.deleteAllPoints()
    }
}
