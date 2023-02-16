package com.example.taskado.Adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ddd.androidutils.DoubleClick
import com.ddd.androidutils.DoubleClickListener
import com.example.taskado.Models.ClickedType
import com.example.taskado.Models.Task
import com.example.taskado.Models.TaskIndex
import com.example.taskado.Models.TaskType
import com.example.taskado.R
import java.lang.String.format
import java.text.DecimalFormat

class Listeners(private val index: TaskAdapter.MyViewHolder, private val clickAtPosition: (TaskIndex)-> Unit): DoubleClickListener {

    override fun onDoubleClickEvent(view: View?) {
        clickAtPosition(TaskIndex(index.bindingAdapterPosition, ClickedType.DoubleClick))
    }

    override fun onSingleClickEvent(view: View?) {
        clickAtPosition(TaskIndex(index.bindingAdapterPosition, ClickedType.SingleClick))
    }
}

class TaskAdapter(private val clickListener: (TaskType) -> Unit) : RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

    private val taskList = ArrayList<Task>()


    class MyViewHolder(itemview: View,clickAtPosition: (TaskIndex)-> Unit) : RecyclerView.ViewHolder(itemview) {
        val title_task : TextView = itemView.findViewById(R.id.title_of_item)
        val creator_task : TextView = itemView.findViewById(R.id.task_creator)
        val date_task : TextView = itemView.findViewById(R.id.date_of_item)
        val precentage_task : TextView = itemView.findViewById(R.id.precentage)
        val card_task : CardView = itemview.findViewById(R.id.card_item)
        val favorite_task_image: ImageView = itemview.findViewById(R.id.task_favorite_image)

        init {

            itemview.setOnLongClickListener() {
                clickAtPosition(TaskIndex(adapterPosition, ClickedType.LongClick))
                true
            }

           itemview.setOnClickListener(DoubleClick(Listeners(this, clickAtPosition)));
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_task,
            parent, false
        )
        return MyViewHolder(itemView) {
            clickListener(TaskType(taskList[it.index],it.type))
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = taskList[position]
        holder.title_task.text = currentitem.task_title
        holder.creator_task.text = currentitem.task_creator
        holder.date_task.text = currentitem.task_start_date +"-"+currentitem.task_start_date
        var precentage = currentitem.mini_task_done.toString().toDouble() / currentitem.task_mini_array.size.toDouble()
        var precentage_string = " "
        when(precentage){
            1.0 -> precentage_string =  "100%"
            else -> {
                precentage_string = "%.2f".format(precentage)
                precentage_string = (precentage_string.toDouble() * 100).toString()+"%"
            }
        }
        holder.precentage_task.text = precentage_string
        when {
            precentage < 0.5 -> holder.card_task.setCardBackgroundColor(
                ContextCompat.getColor(holder.card_task.context, R.color.Orange)
            )
            precentage >= 0.5 && precentage < 1 -> holder.card_task.setCardBackgroundColor(
                ContextCompat.getColor(holder.card_task.context, R.color.purple_200)
            )
            precentage == 1.0 -> holder.card_task.setCardBackgroundColor(
                ContextCompat.getColor(holder.card_task.context, R.color.light)
            )
        }
        when {
            currentitem.task_favorite == 0 -> holder.favorite_task_image.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            currentitem.task_favorite == 1 -> holder.favorite_task_image.setImageResource(R.drawable.ic_favorite_loved_task)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun  updateTaskList(taskList: Collection<Task>) {
        this.taskList.clear()
        this.taskList.addAll(taskList)
        notifyDataSetChanged()
    }
}