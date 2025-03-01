package com.example.myapplication.data

import com.example.myapplication.R

object Constraints {
    fun defaultExerciseList(): ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()
        exerciseList.add(
            ExerciseModel(0,"Push-up",
                R.drawable.pushup
            )
        )
        exerciseList.add(
            ExerciseModel(1,"Pike Push-up",
                R.drawable.pike_pushup
            )
        )
        exerciseList.add(
            ExerciseModel(2,"Squat",
                R.drawable.squat
            )
        )
        exerciseList.add(
            ExerciseModel(3,"Plank",
                R.drawable.plank
            )
        )
        exerciseList.add(
            ExerciseModel(4,"chin-up",
                R.drawable.chin_up
            )
        )
        exerciseList.add(
            ExerciseModel(5,"Lateral raises",
                R.drawable.lateral_raises
            )
        )
        exerciseList.add(
            ExerciseModel(6,"bicep curl",
                R.drawable.biceps_curl
            )
        )
        exerciseList.add(
            ExerciseModel(7,"triceps dip",
                R.drawable.triceps_dip
            )
        )
        exerciseList.add(
            ExerciseModel(8,"lunges",
                R.drawable.lunges
            )
        )
        exerciseList.add(
            ExerciseModel(9,"step up",
                R.drawable.step_up
            )
        )



        return exerciseList
    }
}