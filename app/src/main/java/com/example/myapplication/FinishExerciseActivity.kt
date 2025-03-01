package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.format.Formatter
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.IconCompat.IconType
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityFinishExerciseBinding
import com.example.myapplication.room.ExerciseApp
import com.example.myapplication.room.ExerciseDao
import com.example.myapplication.room.ExerciseEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FinishExerciseActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFinishExerciseBinding
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFinishExerciseBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_finish_exercise)
            setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbarFinishExercise)
        if (supportActionBar != null){
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }
        binding.toolbarFinishExercise.setNavigationOnClickListener {
            onBackPressed()
        }


        binding.btnFinish.setOnClickListener {

            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        val employeeDao = (application as ExerciseApp).db.exerciseDao()
        addExerciseRecord(employeeDao)
    }

    private fun addExerciseRecord(employeeDao : ExerciseDao){
        val currentDateTime = LocalDateTime.now()

        val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

        val formattedDate = currentDateTime.format(dateFormatter)
        val formattedTime = currentDateTime.format(timeFormatter)

        lifecycleScope.launch (Dispatchers.IO){
            employeeDao.insert(ExerciseEntity(date = formattedDate, time = formattedTime))
        }
    }
}