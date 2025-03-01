package com.example.myapplication.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "exercise-table")
data class ExerciseEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val date :String = "",
    val time : String = ""
)



