package com.example.taskado.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.taskado.Models.LocationStatus
import com.example.taskado.R
import com.example.taskado.dataclasses.MiniTask
import com.example.taskado.dataclasses.STATUS

class UpdateMiniTaskAdapter (private val mList: List<MiniTask>, private val clickListener: (LocationStatus) -> Unit) : RecyclerView.Adapter<UpdateMiniTaskAdapter.MiniTaskViewHolder>() {

    class MiniTaskViewHolder(itemView: View, clickAtPosition: (LocationStatus)-> Unit) : RecyclerView.ViewHolder(itemView) {
        val MiniTaskTitle : TextView = itemView.findViewById(R.id.minitask_name)
        val finishedButton: RadioButton = itemView.findViewById(R.id.finish_minitask)
        val unfinshedButton: RadioButton = itemView.findViewById(R.id.unfinish_minitask)
        val doneby: TextView = itemView.findViewById(R.id.done_by_user)
        init {
            finishedButton.setOnClickListener {
                clickAtPosition(LocationStatus(adapterPosition,1))
            }
            unfinshedButton.setOnClickListener {
                clickAtPosition(LocationStatus(adapterPosition,0))
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpdateMiniTaskAdapter.MiniTaskViewHolder {
        return UpdateMiniTaskAdapter.MiniTaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.update_mini_task_view, parent, false)
        ) {
            clickListener(it)
        }
    }
    override fun onBindViewHolder(holder: UpdateMiniTaskAdapter.MiniTaskViewHolder, position: Int) {
        val CurrentTask = mList[position]
        holder.MiniTaskTitle.text = CurrentTask.miniTaskName
        when(CurrentTask.status) {
            STATUS.NOT_FINISHED -> holder.unfinshedButton.isChecked = true
            else -> holder.finishedButton.isChecked = true
        }
        when(CurrentTask.taskCloser){
            null -> holder.doneby.text = "No One"//Resources.getSystem().getString(R.string.no_one)
            else -> holder.doneby.text = CurrentTask.taskCloser

        }
    }
    override fun getItemCount(): Int {
        return mList.size
    }
}

