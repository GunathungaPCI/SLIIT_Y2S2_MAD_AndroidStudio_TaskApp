package com.example.taskpulse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onEditClick: (Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position], position)
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskName: TextView = itemView.findViewById(R.id.taskName)
        private val taskCheckBox: CheckBox = itemView.findViewById(R.id.taskCheckBox)
        private val editButton: Button = itemView.findViewById(R.id.taskEditButton)
        private val deleteButton: Button = itemView.findViewById(R.id.taskDeleteButton)

        fun bind(task: Task, position: Int) {
            taskName.text = task.taskName
            taskCheckBox.isChecked = task.isCompleted

            editButton.setOnClickListener {
                onEditClick(position)
            }

            deleteButton.setOnClickListener {
                onDeleteClick(position)
            }

            // Update CheckBox
            taskCheckBox.setOnCheckedChangeListener { _, isChecked ->
                // Update status
                task.isCompleted = isChecked

                // Notify
                notifyItemChanged(position)
            }
        }
    }
}
