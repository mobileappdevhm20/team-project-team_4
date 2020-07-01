package edu.hm.cs.ma.todoguru.task.completedTask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.databinding.CompletedTaskViewHolderBinding

class CompletedTaskAdapter(
    private val listener: Listener
) : ListAdapter<Task, CompletedTaskAdapter.ViewHolder>(CompletedTaskDiffCallBack()) {

    interface Listener {
        fun onClick(task: Task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position), listener)

    class ViewHolder private constructor(private val binding: CompletedTaskViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CompletedTaskViewHolderBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(task: Task, listener: Listener) {
            binding.task = task
            binding.listener = listener
            binding.executePendingBindings()
        }
    }

    class CompletedTaskDiffCallBack : DiffUtil.ItemCallback<Task>() {

        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}
