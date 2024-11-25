package com.pvz.datadots.presentation.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pvz.datadots.R
import com.pvz.datadots.domain.model.Point
import com.pvz.datadots.domain.repository.PointsRepository
import com.pvz.datadots.presentation.results.ResultsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var pointsRepository: PointsRepository

    private lateinit var pointsInput: EditText
    private lateinit var startButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pointsInput = findViewById(R.id.pointsInput)
        startButton = findViewById(R.id.startButton)

        startButton.setOnClickListener {
            val count = pointsInput.text.toString().toIntOrNull()
            if (count == null || count <= 0) {
                Toast.makeText(this, "Введите корректное число точек", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            fetchPoints(count)
        }
    }

    private fun fetchPoints(count: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = pointsRepository.fetchPoints(count)
                if (response.isSuccessful) {
                    val points = response.body()?.points.orEmpty()
                    withContext(Dispatchers.Main) {
                        openResultsActivity(points)
                    }
                } else {
                    showError("Ошибка: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(MAIN_TAG,"fetchPoints() exception",e)
                showError("Не удалось подключиться к серверу")
            }
        }
    }

    private fun showError(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    private fun openResultsActivity(points: List<Point>) {
        val intent = Intent(this, ResultsActivity::class.java)
        intent.putParcelableArrayListExtra("points", ArrayList(points))
        startActivity(intent)
    }

    companion object {
        private const val MAIN_TAG: String="MainActivity"
    }
}
