package com.pvz.datadots.presentation.results

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.pvz.datadots.R
import com.pvz.datadots.databinding.FragmentHomeBinding
import com.pvz.datadots.databinding.FragmentResultsBinding
import com.pvz.datadots.domain.model.Point
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultsFragment : Fragment() {

    private val viewModel: ResultsViewModel by viewModels()

    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!

    private val saveFileLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    saveGraphToFile(binding.lineChart, uri)
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.results_toast_no_file), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultsBinding.inflate(layoutInflater)
        viewModel.fetchPointsData()

        viewModel.pointList.observe(viewLifecycleOwner) { points ->
            if (points.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.results_toast_no_data), Toast.LENGTH_SHORT
                )
                    .show()
                findNavController().popBackStack()
            }
            with(binding.pointsRecyclerView) {
                layoutManager = LinearLayoutManager(context)
                adapter = PointsAdapter(points)
            }
            setupChart(points)
            viewModel.spinnerId.asLiveData().observe(viewLifecycleOwner) { position ->
                when (position) {
                    0 -> setLineChartMode(LineDataSet.Mode.LINEAR)
                    1 -> setLineChartMode(LineDataSet.Mode.CUBIC_BEZIER)
                    else -> setLineChartMode(LineDataSet.Mode.LINEAR)
                }
            }
        }
        with(binding.graphModeSpinner) {
            val smoothingOptions =
                requireContext().resources.getStringArray(R.array.dropdown_items)
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                smoothingOptions
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            this.adapter = adapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.updateSpinnerItemId(position)
                }

                override fun onNothingSelected(parentView: AdapterView<*>) {
                    // Do nothing
                }
            }
        }
        with(binding.saveGraphButton) {
            setOnClickListener {
                openFileSaveDialog()
            }
        }

        return binding.root
    }

    private fun setupChart(points: List<Point>) {
        val entries = points.sortedBy { it.x }.map { Entry(it.x, it.y) }

        val dataSet = LineDataSet(entries, getString(R.string.graph_label)).apply {
            val theme = requireContext().theme
            color = resources.getColor(android.R.color.holo_blue_dark, theme)
            valueTextColor = resources.getColor(android.R.color.black, theme)
            setDrawCircles(true)
            setDrawCircleHole(false)
            lineWidth = 2f
            circleRadius = 5f
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }

        with(binding.lineChart) {
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

    private fun setLineChartMode(mode: LineDataSet.Mode) {
        val data = binding.lineChart.data
        if (data != null && data.dataSets.isNotEmpty()) {
            val dataSet = data.getDataSetByIndex(0) as LineDataSet
            dataSet.mode = mode
            binding.lineChart.invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isRemoving) {
            viewModel.deleteAllPoints()
        }
    }

    private fun openFileSaveDialog() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/png"
            putExtra(Intent.EXTRA_TITLE, "graph.png")
        }
        saveFileLauncher.launch(intent)
    }

    private fun saveGraphToFile(lineChart: LineChart, uri: Uri) {
        val bitmap = lineChart.chartBitmap

        try {
            requireContext().contentResolver.openOutputStream(uri).use { outputStream ->
                if (outputStream != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.results_toast_success), Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.results_toast_problem), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                getString(R.string.results_toast_error, e.message), Toast.LENGTH_SHORT
            ).show()
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
