package com.example.taskado.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskado.Models.*
import com.example.taskado.R


class FavoriteTaskAdapter(private val taskList: MutableList<TaskR>) :
    RecyclerView.Adapter<FavoriteTaskAdapter.TaskRViewHolder>() {

    class TaskRViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title_task : TextView = itemView.findViewById(R.id.item_favorite_task_title)
        val creator_task : TextView = itemView.findViewById(R.id.item_favorite_task_creator)
        val startdate_task : TextView = itemView.findViewById(R.id.item_favorite_task_start_date)
        val enddate_task : TextView = itemView.findViewById(R.id.item_favorite_task_end_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskRViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite_task, parent, false)
        return TaskRViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskRViewHolder, position: Int) {
        val currentTask = taskList[position]
        holder.title_task.text = currentTask.task_title
        holder.creator_task.text = currentTask.task_creator
        holder.startdate_task.text = currentTask.task_start_date
        holder.enddate_task.text = currentTask.task_end_date
    }

    override fun getItemCount() = taskList.size
    fun updateData(newTaskRList: MutableList<TaskR>) {
        this.taskList.clear()
        this.taskList.addAll(newTaskRList)
        notifyDataSetChanged()
    }
}