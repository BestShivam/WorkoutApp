package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.myapplication.databinding.ActivityExerciseHistoryBinding
import com.example.myapplication.room.ExerciseApp
import com.example.myapplication.room.ExerciseDao
import com.example.myapplication.room.ExerciseEntity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ExerciseHistoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityExerciseHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityExerciseHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.tbHistory)
        if (supportActionBar != null){
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.title = "Exercise History"
        }
        binding.tbHistory.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        binding.tbHistory.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        val employeeDao = (application as ExerciseApp).db.exerciseDao()
        getExerciseRecord(employeeDao)
    }

    private fun getExerciseRecord(employeeDao : ExerciseDao){
        lifecycleScope.launch {
            employeeDao.getAllExercises().collectLatest { exerciseList ->
                if (exerciseList.isNotEmpty()) {
                    binding.rvHistory.adapter = ExerciseHistoryAdapter(exerciseList)
                } else {
                    Log.d("ExerciseHistoryActivity", "No records found")
                }
            }
        }
    }


}


