package com.pvz.datadots.presentation.results

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.pvz.datadots.R
import com.pvz.datadots.domain.model.Point
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val points = intent.getParcelableArrayListExtra<Point>("points") ?: emptyList()

        if (points.isEmpty()) {
            Toast.makeText(this, "Нет данных для отображения", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val recyclerView: RecyclerView = findViewById(R.id.pointsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PointsAdapter(points)

        val lineChart: LineChart = findViewById(R.id.lineChart)
        setupChart(lineChart, points)
    }

    private fun setupChart(lineChart: LineChart, points: List<Point>) {
        val entries = points.sortedBy { it.x }.map { Entry(it.x, it.y) }

        val dataSet = LineDataSet(entries, "График точек").apply {
            color = resources.getColor(android.R.color.holo_blue_dark, theme)
            valueTextColor = resources.getColor(android.R.color.black, theme)
            setDrawCircles(true)
            setDrawCircleHole(false)
            lineWidth = 2f
            circleRadius = 5f
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }

        lineChart.data = LineData(dataSet)

        lineChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f
        }
        lineChart.axisLeft.isEnabled = true
        lineChart.axisRight.isEnabled = false

        lineChart.description.isEnabled = false
        lineChart.invalidate()
    }
}
