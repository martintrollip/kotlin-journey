package com.example.android.architecture.blueprints.todoapp.util

/**
 * Extension functions for Fragment.
 */

import androidx.fragment.app.Fragment
import com.example.android.architecture.blueprints.todoapp.TodoApplication
import com.example.android.architecture.blueprints.todoapp.ViewModelFactory

fun Fragment.getViewModelFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as TodoApplication).tasksRepository
    return ViewModelFactory(repository)
}