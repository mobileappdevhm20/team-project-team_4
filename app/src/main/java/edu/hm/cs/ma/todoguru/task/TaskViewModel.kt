package edu.hm.cs.ma.todoguru.task

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.hm.cs.ma.todoguru.database.Task
import edu.hm.cs.ma.todoguru.database.TaskDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime

class TaskViewModel(
    private val database: TaskDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    class Factory(
        private val dataBase: TaskDatabaseDao,
        private val application: Application
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
                return TaskViewModel(dataBase, application) as T
            }
            throw IllegalAccessException("unknown viewModel class")
        }
    }

    val tasks = database.getAllTask()

    private var _addTaskEvent = MutableLiveData(false)
    val addTaskEvent: LiveData<Boolean>
        get() = _addTaskEvent

    private var _showTaskFragment = MutableLiveData<Task?>()
    val showTaskFragment: LiveData<Task?>
        get() = _showTaskFragment

    private var task: Task? = null

    private var _markTaskDoneEvent = MutableLiveData(false)
    val markTaskDoneEvent: LiveData<Boolean>
        get() = _markTaskDoneEvent

    private var _deleteTaskEvent = MutableLiveData(false)
    val deleteTaskEvent: LiveData<Boolean>
        get() = _deleteTaskEvent

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun triggerTaskEvent() {
        _addTaskEvent.value = true
    }

    fun insertTask(
        title: String,
        description: String,
        dueDate: LocalDate,
        estimated: Int,
        reminder: LocalDateTime
    ) {
        uiScope.launch {
            insert(Task(title, description, dueDate, estimated, reminder))
        }
    }

    fun updateTask(
        id: Long,
        title: String,
        description: String,
        dueDate: LocalDate,
        estimated: Int,
        reminder: LocalDateTime
    ) {
        uiScope.launch {
            update(Task(id, title, description, dueDate, estimated, reminder))
        }
    }

    fun markTasksAsDone(tasks: List<Task>) {
        uiScope.launch {
            tasks.forEach {
                it.done = true
                update(it)
            }
        }.invokeOnCompletion {
            _markTaskDoneEvent.value = true
        }
    }

    fun deleteTasks(tasks: List<Task>) {
        uiScope.launch {
            tasks.forEach {
                delete(it)
            }
        }.invokeOnCompletion {
            _deleteTaskEvent.value = true
        }
    }

    private suspend fun insert(task: Task) {
        withContext(Dispatchers.IO) {
            database.insert(Task(task))
        }
    }

    private suspend fun update(task: Task) {
        withContext(Dispatchers.IO) {
            database.update(task)
        }
    }

    private suspend fun delete(task: Task) {
        withContext(Dispatchers.IO) {
            database.delete(task)
        }
    }
}
