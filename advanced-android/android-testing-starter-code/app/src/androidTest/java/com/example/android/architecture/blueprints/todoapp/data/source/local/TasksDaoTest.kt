package com.example.android.architecture.blueprints.todoapp.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Martin Trollip
 * @since 2020/06/02 17:41
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest //Small test
class TasksDaoTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule() //Because we're testing archi components

    private lateinit var database: ToDoDatabase

    @Before
    fun initDb() {
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
                getApplicationContext(),
                ToDoDatabase::class.java
        ).build()
    }

    //for cleaning up your database using database.close():
    @After
    fun closeDb() = database.close()

    @Test
    fun insertTaskAndGetById() = runBlockingTest {
        // GIVEN - Insert a task.
        val task = Task("title", "description")
        database.taskDao().insertTask(task)

        // WHEN - Get the task by id from the database.
        val loaded = database.taskDao().getTaskById(task.id)

        // THEN - The loaded data contains the expected values.
        assertThat<Task>(loaded as Task, notNullValue(Task::class.java))
        assertThat(loaded.id, `is`(task.id))
        assertThat(loaded.title, `is`(task.title))
        assertThat(loaded.description, `is`(task.description))
        assertThat(loaded.isCompleted, `is`(task.isCompleted))
    }

    @Test
    fun updateTaskAndGetById() = runBlockingTest {
        // 1. Insert a task into the DAO.
        val originalTask = Task("title", "description")
        database.taskDao().insertTask(originalTask)

        // 2. Update the task by creating a new task with the same ID but different attributes.
        val updatedTask = Task("new title", "new description", true, originalTask.id)
        database.taskDao().updateTask(updatedTask)

        // 3. Check that when you get the task by its ID, it has the updated values.
        val loaded = database.taskDao().getTaskById(originalTask.id)
        assertThat(loaded?.id, `is`(originalTask.id))
        assertThat(loaded?.title, `is`("new title"))
        assertThat(loaded?.description, `is`("new description"))
        assertThat(loaded?.isCompleted, `is`(true))
    }
}