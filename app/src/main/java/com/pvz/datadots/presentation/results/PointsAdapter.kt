package com.pvz.datadots.presentation.results

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pvz.datadots.R
import com.pvz.datadots.domain.model.Point

class PointsAdapter(private val points: List<Point>) :
    RecyclerView.Adapter<PointsAdapter.PointViewHolder>() {

    class PointViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val xTextView: TextView = view.findViewById(R.id.xTextView)
        val yTextView: TextView = view.findViewById(R.id.yTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_point, parent, false)
        return PointViewHolder(view)
    }

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        val point = points[position]
        holder.xTextView.text = "x: ${point.x}"
        holder.yTextView.text = "y: ${point.y}"
    }

    override fun getItemCount(): Int = points.size
}
