/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.architecture.blueprints.todoapp.data.source

import androidx.lifecycle.LiveData
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import java.lang.Exception

/**
 * Test double, this is a a fake which will provide a real enough representation of the functionality of the class, but it's not
 * something which you want to deploy to prod
 *
 * This wont actually use a database or network connection.
 *
 * Implement the relevant methods 1st, getTask, Save and Delete
 */
class FakeDataSource(var tasks: MutableList<Task>? = mutableListOf()) : TasksDataSource { //Implement the same interface as the original class/dependency

    override suspend fun saveTask(task: Task) {
        tasks?.add(task) //Fake implementation. Just add the tasks to the tasks list in the constructor
    }

    override suspend fun deleteAllTasks() {
        tasks?.clear()
    }

    override suspend fun getTasks(): Result<List<Task>> {
        tasks?.let {
            return Result.Success(ArrayList(it))
        }
        return Result.Error(Exception("Tasks not found"))
    }

    override fun observeTasks(): LiveData<Result<List<Task>>> {
        throw NotImplementedError()
    }

    override suspend fun refreshTasks() {
        throw NotImplementedError()
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        throw NotImplementedError()
    }

    override suspend fun getTask(taskId: String): Result<Task> {
        throw NotImplementedError()
    }

    override suspend fun refreshTask(taskId: String) {
        throw NotImplementedError()
    }

    override suspend fun completeTask(task: Task) {
        throw NotImplementedError()
    }

    override suspend fun completeTask(taskId: String) {
        throw NotImplementedError()
    }

    override suspend fun activateTask(task: Task) {
        throw NotImplementedError()
    }

    override suspend fun activateTask(taskId: String) {
        throw NotImplementedError()
    }

    override suspend fun clearCompletedTasks() {
        throw NotImplementedError()
    }

    override suspend fun deleteTask(taskId: String) {
        throw NotImplementedError()
    }
}
