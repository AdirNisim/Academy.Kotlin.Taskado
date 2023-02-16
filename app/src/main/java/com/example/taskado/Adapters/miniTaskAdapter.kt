import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskado.R
import com.example.taskado.dataclasses.MiniTask


class MiniTaskAdapter(private val mList: List<MiniTask>,private val clickListener: (MiniTask) -> Unit) : RecyclerView.Adapter<MiniTaskAdapter.MiniTaskViewHolder>() {

    class MiniTaskViewHolder(itemView: View,clickAtPosition: (Int)-> Unit) : RecyclerView.ViewHolder(itemView) {
        val MiniTaskTitle : TextView = itemView.findViewById(R.id.minitask_name)
        init {
            itemView.setOnClickListener {
                clickAtPosition(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiniTaskViewHolder {
        return MiniTaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.minitask_view, parent, false)) {
            clickListener(mList[it])
        }
    }
    override fun onBindViewHolder(holder: MiniTaskViewHolder, position: Int) {
        val CurrentTask = mList[position]
        holder.MiniTaskTitle.text = CurrentTask.miniTaskName
    }
    override fun getItemCount(): Int {
        return mList.size
    }
}


