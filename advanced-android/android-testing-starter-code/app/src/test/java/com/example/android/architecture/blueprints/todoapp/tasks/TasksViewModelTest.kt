package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider //The usage of this as well as the @RunWith AndroidJUnit4 can actually be avoided if we use the FakeTaskRepository instead of the actually Repository
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.Event
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTasksRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Unit test for the view model.
 *
 * Pure ViewModel tests can goto `test` (local tests) folder since the ViewModelCode should not depend on the Android framework
 *
 * @author Martin Trollip ***REMOVED***
 * @since 2020/03/14 18:17
 */
//@RunWith(AndroidJUnit4::class)  //Only needed if we need some Android constructs
@ExperimentalCoroutinesApi
class TasksViewModelTest {

    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var tasksRepository: FakeTasksRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule() //Runs all architecture components background tasks on the same thread. Add this when testing LiveData

    // Subject under test
    private lateinit var taskViewModel: TasksViewModel

    @Before
    fun setupViewModel() {
        Dispatchers.setMain(testDispatcher)

        tasksRepository = FakeTasksRepository()

        val task1 = Task("Title1", "Description1")
        val task2 = Task("Title2", "Description2", true)
        val task3 = Task("Title3", "Description3", true)
        tasksRepository.addTasks(task1, task2, task3)

        taskViewModel = TasksViewModel(tasksRepository) // You want to initialse taskViewModel here to make sure each test run has it's own instance
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    //Can't use the ViewModelProvider here since it's not an `androidTest`.
    //
    // Build one manually but that requires an application context. We can use AndroidX tests and Robolectric for this.
    @Test
    fun addNewTask_setsNewTaskEvent_withBoilerPlateCode() {
        //GIVEN a fresh ViewModel
        // (init in @Before)

        val observer = Observer<Event<Unit>> {} //Live data has to be observed
        try {
            taskViewModel.newTaskEvent.observeForever(observer) //Use forever since we dont have a lifecycle owner

            //WHEN adding a new task
            taskViewModel.addNewTask() //It's being observed so we can get the value

            //THEN the new task event is triggered
            val value = taskViewModel.newTaskEvent.value
            assertThat(value?.getContentIfNotHandled(), not(nullValue())) //Check the value
        } finally {
            taskViewModel.newTaskEvent.removeObserver(observer) //Remove the observer to prevent leaks
        }
    }

    @Test
    fun addNewTask_setsNewTaskEvent_withLiveDataTestUtils() {
        //GIVEN a fresh ViewModel
        // (init in @Before)

        //WHEN adding a new task
        taskViewModel.addNewTask()
        val value = taskViewModel.newTaskEvent.getOrAwaitValue()

        //THEN the new task event is triggered
        assertThat(value.getContentIfNotHandled(), not(nullValue()))
    }

    @Test
    fun setFilterAllTasks_tasksAddViewVisible() {
        // GIVEN a fresh ViewModel
        // (init in @Before)

        // WHEN the filter type is ALL_TASKS
        taskViewModel.setFiltering(TasksFilterType.ALL_TASKS)

        // THEN the "Add task" action is visible
        val addTaskVisibility = taskViewModel.tasksAddViewVisible.getOrAwaitValue()
        assertThat(addTaskVisibility, `is`(true))
    }

    @Test
    fun completeTask_dataAndSnackbarUpdated() {
        // Create an active task and add it to the repository.
        val task = Task("Title", "Description")
        tasksRepository.addTasks(task)

        // Mark the task as complete task.
        taskViewModel.completeTask(task, true)

        // Verify the task is completed.
        assertThat(tasksRepository.tasksServiceData[task.id]?.isCompleted, `is`(true))

        // Assert that the snackbar has been updated with the correct text.
        val snackbarText: Event<Int> =  taskViewModel.snackbarText.getOrAwaitValue()
        assertThat(snackbarText.getContentIfNotHandled(), `is`(R.string.task_marked_complete))
    }
}