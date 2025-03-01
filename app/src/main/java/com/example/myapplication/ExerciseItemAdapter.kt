package com.example.myapplication

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.ExerciseModel

class ExerciseItemAdapter (val mlist : ArrayList<ExerciseModel>):
    RecyclerView.Adapter<ExerciseItemAdapter.ViewHolder>(){
    class ViewHolder(private val itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvExercise :TextView = itemView.findViewById(R.id.tvItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_design,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentExercise : ExerciseModel = mlist[position]
        holder.tvExercise.text = (currentExercise.getId() + 1).toString()
        when{
            currentExercise.getIsSelected()->{
                holder.tvExercise.setBackgroundResource(R.drawable.item_circular_background_light)
            }
            currentExercise.getIsCompleted() ->{
                holder.tvExercise.setBackgroundResource(R.drawable.item_circular_background_green)
                holder.tvExercise.setTextColor(Color.WHITE)
            }
            else ->{

                holder.tvExercise.setBackgroundResource(R.drawable.item_circular_background)
                holder.tvExercise.setTextColor(Color.BLACK)

            }
        }

    }
}