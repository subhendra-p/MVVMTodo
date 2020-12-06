package com.codinginflow.mvvmtodo.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.codinginflow.mvvmtodo.data.TaskDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class TasksViewModel @ViewModelInject constructor(taskDao: TaskDao) : ViewModel() {
    val searchQuery  = MutableStateFlow("")

    private val taskFlow = searchQuery.flatMapLatest {
        taskDao.getTask(it)
    }

    val tasks = taskFlow.asLiveData()
}