package com.example.android.architecture.blueprints.todoapp.taskdetail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Martin Trollip ***REMOVED***
 * @since 2020/03/17 18:40
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
class TaskDetailFragmentTest {

    //There is no easy way to pass the repository to the fragment. We cant add a Fake via constructor DI.  Now what?
    @Test
    fun activeTaskDetails_DisplayedInUi() {
        // GIVEN - Add active (incomplete) task to the DB
        val activeTask = Task("Active Task", "AndroidX Rocks", false)

        // WHEN - Details fragment launched to display task
        val bundle = TaskDetailFragmentArgs(activeTask.id).toBundle()
        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme) //We specify the apptheme because launchFragmentInContainer uses an empty activity, with no theme, and fragments usually inherit the theme from the activity
        Thread.sleep(2000)
    }

}
