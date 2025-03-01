package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.room.ExerciseEntity

//ExerciseHistoryAdapter

class  ExerciseHistoryAdapter(private val historyList: List<ExerciseEntity>)
    : RecyclerView.Adapter<ExerciseHistoryAdapter.ViewHolder>(){

    class ViewHolder (itemView :View) : RecyclerView.ViewHolder(itemView){
        val tvId : TextView = itemView.findViewById(R.id.textViewId)
        val tvDate : TextView = itemView.findViewById(R.id.textViewDate)
        val tvTime : TextView = itemView.findViewById(R.id.textViewTime)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_history_row_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val exercise = historyList[position]
        holder.tvId.text = exercise.id.toString()
        holder.tvDate.text = exercise.date
        holder.tvTime.text = exercise.time
    }
}