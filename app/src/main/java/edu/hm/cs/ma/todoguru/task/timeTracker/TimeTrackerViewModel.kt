package edu.hm.cs.ma.todoguru.task.timeTracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.hm.cs.ma.todoguru.database.Time
import edu.hm.cs.ma.todoguru.database.TimeTrackerDatabaseDao
import edu.hm.cs.ma.todoguru.task.formatTimes
import kotlinx.coroutines.* // ktlint-disable no-wildcard-imports
import timber.log.Timber

class TimeTrackerViewModel(
    private val taskID: Long,
    val database: TimeTrackerDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    class Factory(
        private val taskID: Long,
        private val dataSource: TimeTrackerDatabaseDao,
        private val application: Application
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TimeTrackerViewModel::class.java)) {
                return TimeTrackerViewModel(taskID, dataSource, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var time = MutableLiveData<Time?>()
    private val times = database.getTimesOfTask(this.taskID)

    val timesString = Transformations.map(times) { times ->
        formatTimes(times, application.resources)
    }

    val startButtonVisible = Transformations.map(time) {
        null == it
    }

    val stopButtonVisible = Transformations.map(time) {
        null != it
    }

    init {
        initializeTime()
    }

    private fun initializeTime() {
        uiScope.launch {
            time.value = getTimeFromDatabase()
        }
    }

    private suspend fun getTimeFromDatabase(): Time? {
        return withContext(Dispatchers.IO) {
            var ti = database.getTime()
            if (ti?.endTimeMilli != ti?.startTimeMilli) {
                ti = null
            }
            ti
        }
    }

    private suspend fun update(time: Time) {
        Timber.d("Update the time spent on a task")
        withContext(Dispatchers.IO) {
            database.update(time)
        }
    }

    private suspend fun insert(time: Time) {
        Timber.d("Insert the time spent on a task")
        withContext(Dispatchers.IO) {
            database.insert(time)
        }
    }

    fun onStartTracking() {
        Timber.d("Start tracking the time spent on a task")
        uiScope.launch {
            val newTime = Time(0, taskID)

            insert(newTime)

            time.value = getTimeFromDatabase()
        }
    }

    fun onStopTracking() {
        Timber.d("Stop tracking the time spent on a task")
        uiScope.launch {
            val oldTime = time.value ?: return@launch

            oldTime.endTimeMilli = System.currentTimeMillis()

            update(oldTime)
            time.value = null
        }
    }

    /**
     * Called when the ViewModel is dismantled.
     * At this point, we want to cancel all coroutines;
     * otherwise we end up with processes that have nowhere to return to
     * using memory and resources.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
