package mx.edu.itesca.practica14

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter (
    var tasksList : List<Task>,
    var onDeleteClick: (String) -> Unit,
    var onUpdateClick: (Task) -> Unit
): RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val cvTask: CardView = itemView.findViewById(R.id.cv_task)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvDesc: TextView = itemView.findViewById(R.id.tv_description)
        val ibtnDelete: ImageButton = itemView.findViewById(R.id.ibtn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_task,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasksList[position]

        holder.tvTitle.text = task.title
        holder.tvDesc.text = task.description

        holder.ibtnDelete.setOnClickListener{
            onDeleteClick(task.id)
        }

        holder.cvTask.setOnClickListener {
            onUpdateClick(task)
        }
    }
}