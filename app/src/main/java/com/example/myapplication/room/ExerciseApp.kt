package com.example.myapplication.room

import android.app.Application

class ExerciseApp : Application() {
    val db by lazy {
        ExerciseDatabase.getInstance(this)
    }
}