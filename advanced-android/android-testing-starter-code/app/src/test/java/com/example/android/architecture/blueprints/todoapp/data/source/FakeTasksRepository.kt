package com.example.android.architecture.blueprints.todoapp.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.runBlocking

/**
 * This class is a fake an it allows us to test the entire view model, without us having to worry about its dependencies.
 *
 * The Fake class and the real class will share a common interface.
 *
 * @author Martin Trollip ***REMOVED***
 * @since 2020/03/16 19:22
 */
class FakeTasksRepository : TasksRepository {
    var tasksServiceData: LinkedHashMap<String, Task> = LinkedHashMap() //the current list of tasks
    private val observableTasks = MutableLiveData<Result<List<Task>>>() //for observable tasks

    //This method should just take the tasksServiceData and turn it into a list using tasksServiceData.values.toList() and then return that as a Success result.
    override suspend fun getTasks(forceUpdate: Boolean): Result<List<Task>> {
        return Result.Success(tasksServiceData.values.toList())
    }

    //Updates the value of observableTasks to be what is returned by getTasks().
    override suspend fun refreshTasks() {
        observableTasks.value = getTasks()
    }

    // Create a coroutine using runBlocking and run refreshTasks, then return observableTasks.
    override fun observeTasks(): LiveData<Result<List<Task>>> {
        runBlocking { refreshTasks() }
        return observableTasks
    }

    //This is a helper method to assist with testing
    //Takes in a vararg of tasks, adds each to the HashMap and then refreshes the tasks:
    fun addTasks(vararg tasks: Task) {
        for (task in tasks) {
            tasksServiceData[task.id] = task
        }
        runBlocking { refreshTasks() }
    }

    override suspend fun refreshTask(taskId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getTask(taskId: String, forceUpdate: Boolean): Result<Task> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveTask(task: Task) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun completeTask(task: Task) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun completeTask(taskId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun activateTask(task: Task) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun activateTask(taskId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun clearCompletedTasks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteAllTasks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteTask(taskId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}