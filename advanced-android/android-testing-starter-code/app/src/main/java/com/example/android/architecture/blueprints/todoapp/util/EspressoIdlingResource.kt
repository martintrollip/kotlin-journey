package com.example.android.architecture.blueprints.todoapp.util

import androidx.test.espresso.idling.CountingIdlingResource

/**
 * Idling resource related to long running repository operations.
 *
 * @author Martin Trollip
 * @since 2020/06/15 08:40
 */
object EspressoIdlingResource {

    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}

inline fun <T> wrapEspressoIdlingResource(function: () -> T): T {
    // Espresso does not work well with coroutines yet. See
    // https://github.com/Kotlin/kotlinx.coroutines/issues/982
    EspressoIdlingResource.increment() // Set app as busy.
    return try {
        function()
    } finally {
        EspressoIdlingResource.decrement() // Set app as idle.
    }
}

/**
Step 5: Create an Idling Resource Singleton
You will add two idling resources. One to deal with data binding synchronization for your views, and another to deal with the long running operation in your repository.

You'll start with the idling resource related to long running repository operations.

Create a new file called EspressoIdlingResource in app > java > main > util:

Step 6: Create wrapEspressoIdlingResource
In the EspressoIdlingResource file, below the singleton you just created, add the following code for wrapEspressoIdlingResource:

Step 7: Use wrapEspressoIdlingResource in DefaultTasksRepository
Now you should wrap long running operations with wrapEspressoIdlingResource. The majority of these are in your DefaultTasksRepository.

In your application code, open data > source > DefaultTasksRepository.
Wrap all methods in DefaultTasksRepository with wrapEspressoIdlingResource
**/
