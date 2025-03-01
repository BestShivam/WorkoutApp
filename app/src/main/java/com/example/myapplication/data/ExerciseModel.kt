package com.example.myapplication.data

class ExerciseModel(
    private val id : Int,
    private val name : String,
    private val image : Int,
    private var isCompleted : Boolean = false,
    private var isSelected : Boolean = false
) {
    fun getId():Int{
        return this.id
    }
    fun getName():String{
        return this.name
    }
    fun getImage():Int{
        return this.image
    }
    fun setIsCompleted(complete : Boolean){
        this.isCompleted = complete
    }
    fun setIsSelected(selected : Boolean){
        this.isSelected = selected
    }

    fun getIsCompleted():Boolean{
        return this.isCompleted
    }
    fun getIsSelected():Boolean{
        return this.isSelected
    }

}