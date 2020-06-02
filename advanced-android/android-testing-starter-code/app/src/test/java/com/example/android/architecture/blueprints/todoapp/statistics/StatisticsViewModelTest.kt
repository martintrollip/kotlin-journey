package com.example.android.architecture.blueprints.todoapp.statistics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTasksRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author Martin Trollip ***REMOVED***
 * @since 2020/04/14 10:50
 */
@ExperimentalCoroutinesApi
class StatisticsViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Subject under test
    private lateinit var statisticsViewModel: StatisticsViewModel

    // Use a fake repository to be injected into the view model.
    private lateinit var tasksRepository: FakeTasksRepository

    @Before
    fun setupStatisticsViewModel() {
        // Initialise the repository with no tasks.
        tasksRepository = FakeTasksRepository()

        statisticsViewModel = StatisticsViewModel(tasksRepository)
    }

    //What we want to test for example:  when swiping to refresh we want to be able to see the refresh spinner
    @Test
    fun loadTasks_loading() {
        mainCoroutineRule.pauseDispatcher()
        statisticsViewModel.refresh()

        //We want to check that the spinner was shown
        //But since we're using the test coroutine dispatcher, all of the refresh code is run deterministically and the dataLoading state is set to false before we hit the next line and the loading indicator is gone (
        //This is good for fast tests.  But in this particular case we want to test this specific state in the middle of execution
        //We want to assert before the coroutine starts and just when it finished
        //Add the pause and resume dispatchers of the test coroutine dispatcher
        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue(), `is`(true))

        //Some time later we want that spinner to go away
        mainCoroutineRule.resumeDispatcher()
        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue(), `is`(false))

        //Test coroutine dispatcher includes more fine grained timing functions
    }

    @Test
    fun loadStatisticsWhenTasksAreUnavailable_callErrorToDisplay() {
        // Make the repository return errors.
        tasksRepository.setReturnError(true)
        statisticsViewModel.refresh()

        // Then empty and error are true (which triggers an error message to be shown).
        assertThat(statisticsViewModel.empty.getOrAwaitValue(), `is`(true))
        assertThat(statisticsViewModel.error.getOrAwaitValue(), `is`(true))
    }
}