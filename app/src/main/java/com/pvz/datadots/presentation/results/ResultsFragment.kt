package com.pvz.datadots.presentation.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.pvz.datadots.R
import com.pvz.datadots.databinding.FragmentResultsBinding
import com.pvz.datadots.domain.model.Point
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultsFragment : Fragment() {

    private val binding by lazy {
        FragmentResultsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //val points = intent.getParcelableArrayListExtra<Point>("points") ?: emptyList()
        val points = emptyList<Point>()
        if (points.isEmpty()) {
            //Toast.makeText(this, "Нет данных для отображения", Toast.LENGTH_SHORT).show()
            //finish()
        }
        with(binding.pointsRecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = PointsAdapter(points)
        }

        setupChart(points)
        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    private fun setupChart(points: List<Point>) {
        val entries = points.sortedBy { it.x }.map { Entry(it.x, it.y) }

        val dataSet = LineDataSet(entries, "График точек").apply {
            //color = resources.getColor(android.R.color.holo_blue_dark, theme)
            //valueTextColor = resources.getColor(android.R.color.black, theme)
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
}
