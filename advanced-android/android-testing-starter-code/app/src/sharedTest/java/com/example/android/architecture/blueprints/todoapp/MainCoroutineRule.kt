package com.example.android.architecture.blueprints.todoapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * @author Martin Trollip ***REMOVED***
 * @since 2020/04/14 09:32
 */
@ExperimentalCoroutinesApi
class MainCoroutineRule(val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()):
        TestWatcher(),
        TestCoroutineScope by TestCoroutineScope(dispatcher) {
    override fun starting(description: Description?) {//@Before
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) { //@After
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}