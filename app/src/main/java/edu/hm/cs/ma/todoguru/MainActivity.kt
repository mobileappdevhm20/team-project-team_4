package edu.hm.cs.ma.todoguru

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.database.TaskDatabase
import edu.hm.cs.ma.todoguru.databinding.ActivityMainBinding
import edu.hm.cs.ma.todoguru.task.InsertTaskDialog
import edu.hm.cs.ma.todoguru.task.TaskAdapter
import edu.hm.cs.ma.todoguru.task.TaskViewModel
import edu.hm.cs.ma.todoguru.task.TaskViewModelFactory
import edu.hm.cs.ma.todoguru.task.TaskWrapper
import java.time.LocalDate
import kotlinx.android.synthetic.main.activity_main.topAppBar

class MainActivity : InsertTaskDialog.Listener, TaskAdapter.Listener, AppCompatActivity() {

    private lateinit var viewModel: TaskViewModel

    private val selectedTasks = ArrayList<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao
        val viewModelFactory = TaskViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = TaskAdapter(this)
        binding.tasksList.adapter = adapter

        viewModel.tasks.observe(this, Observer {
            it.let { adapter.submitList(it) }
        })

        viewModel.addTaskEvent.observe(this, Observer {
            if (it)
                InsertTaskDialog(this).show(supportFragmentManager, InsertTaskDialog.TAG)
        })

        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.mark_tasks_as_done -> {
                    viewModel.markTasksAsDone(selectedTasks)
                    true
                }
                R.id.delete_tasks -> {
                    viewModel.deleteTasks(selectedTasks)
                    true
                }
                else  -> false
            }
        }

        viewModel.markTaskDoneEvent.observe(this, Observer {
            if (it)
                selectedTasks.clear()
        })

        viewModel.deleteTaskEvent.observe(this, Observer {
            if (it)
                selectedTasks.clear()
        })
    }

    override fun onInsertTask(
        title: String,
        description: String,
        dueDate: LocalDate,
        estimated: Int,
        reminder: String
    ) {
        viewModel.insertTask(title, description, dueDate, estimated, reminder)
    }

    override fun onCheckBoxClick(wrapper: TaskWrapper) {
        val task = wrapper.task
        if (selectedTasks.contains(task))
            selectedTasks.remove(task)
        else
            selectedTasks.add(task)
        wrapper.isSelected = true
    }
}
