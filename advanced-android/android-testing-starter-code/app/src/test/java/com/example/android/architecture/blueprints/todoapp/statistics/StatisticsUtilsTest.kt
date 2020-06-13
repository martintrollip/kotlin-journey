package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

/**
 * @author Martin Trollip
 * @since 2020/03/14 13:57
 */
class StatisticsUtilsTest {

    //If there's no completed tasks and one active task we should have 0% completed and 100% active
    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsHundredZero() {

        // Create an active tasks (the false makes this active)
        val tasks = listOf<Task>(
                Task("title", "desc", isCompleted = false)
        )
        // Call our function
        val result = getActiveAndCompletedStats(tasks)

        // Check the result
        assertEquals(result.completedTasksPercent, 0f)
        assertEquals(result.activeTasksPercent, 100f)
    }

    //If there's one completed task and none active task we should have 100% completed and 0% active
    @Test
    fun getActiveAndCompletedStats_noActive_returnsZeroHundred() {

        // Create an active tasks (the false makes this active)
        val tasks = listOf(
                Task("title", "desc", isCompleted = true)
        )
        // Call our function
        val result = getActiveAndCompletedStats(tasks)

        // Check the result
        assertEquals(result.completedTasksPercent, 100f)
        assertEquals(result.activeTasksPercent, 0f)
    }

    // If there's no tasks return 0% completed and 0% active
    // You can easily write this test 1st to recreate a scenario where someone reported a bug, say for example my app always crahses
    // when there's no tasks.    You can easily test that scenario and debug it without having to reproduce it manually on your
    // own device.
    @Test
    fun getActiveAndCompletedStats_noTasks_returnsZeroZero() {

        // Create an active tasks (the false makes this active)
        val tasks = emptyList<Task>()

        // Call our function
        val result = getActiveAndCompletedStats(tasks)

        // Check the result
        assertEquals(result.completedTasksPercent, 0f)
        assertEquals(result.activeTasksPercent, 0f)
    }

    //If there's 1 completed tasks and 1 active tasks, return 50 50
    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsFiftyFifty() {

        // Create an active tasks (the false makes this active)
        val tasks = listOf(
                Task("title", "desc", isCompleted = false),
                Task("title 2", "desc 2", isCompleted = true)
        )
        // Call our function
        val result = getActiveAndCompletedStats(tasks)

        // Check the result
        assertEquals(result.completedTasksPercent, 50f)
        assertEquals(result.activeTasksPercent, 50f)
    }


    //Instructors anwers,  check the assertThat way of doing things.
    @Test
    fun getActiveAndCompletedStats_noActive_returnsZeroHundred_instructors() {
        //GIVEN a list of tasks with a single completed task
        //This is the input
        val tasks = listOf(
                Task("title", "desc", isCompleted = true)
        )

        //WHEN you calculate the percentage active and completed task
        // This is doing something
        val result = getActiveAndCompletedStats(tasks)

        //THEN there are 0% active tasks and 100% completed tasks.
        // This is checking the answer
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(100f))  //<<< assertion framework called hamcrest
    }

    @Test
    fun getActiveAndCompletedStats_both_returnsFortySixty() {
        // Given 3 completed tasks and 2 active tasks
        val tasks = listOf(
                Task("title", "desc", isCompleted = true),
                Task("title", "desc", isCompleted = true),
                Task("title", "desc", isCompleted = true),
                Task("title", "desc", isCompleted = false),
                Task("title", "desc", isCompleted = false)
        )
        // When the list of tasks is computed
        val result = getActiveAndCompletedStats(tasks)

        // Then the result is 40-60
        assertThat(result.activeTasksPercent, `is`(40f))
        assertThat(result.completedTasksPercent, `is`(60f))
    }

    @Test
    fun getActiveAndCompletedStats_error_returnsZeros() {
        // When there's an error loading stats
        val result = getActiveAndCompletedStats(null)

        // Both active and completed tasks are 0
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompletedStats_empty_returnsZeros() {
        // When there are no tasks
        val result = getActiveAndCompletedStats(emptyList())

        // Both active and completed tasks are 0
        assertThat(result.activeTasksPercent, `is`(0f))
        assertThat(result.completedTasksPercent, `is`(0f))
    }


}