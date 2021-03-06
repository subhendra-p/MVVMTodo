package com.codinginflow.mvvmtodo.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.codinginflow.mvvmtodo.data.TaskDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

class TasksViewModel @ViewModelInject constructor(taskDao: TaskDao) : ViewModel() {
    val searchQuery  = MutableStateFlow("")

//    private val taskFlow = searchQuery.flatMapLatest {
//        taskDao.getTask(it)
//    }

    val sortOrder = MutableStateFlow(SortOrder.BY_NAME)
    val hideCompleted = MutableStateFlow(false)

    private val taskFlow = combine(searchQuery, sortOrder, hideCompleted) {
searchQuery, sortOrder, hideCompleted ->
        Triple(searchQuery, sortOrder, hideCompleted)
    }.flatMapLatest { (searchQuery, sortOrder, hideCompleted) ->
        taskDao.getTask(searchQuery = searchQuery,sortOrder =  sortOrder, hideCompleted =  hideCompleted)
    }

    val tasks = taskFlow.asLiveData()
}

enum class SortOrder{
    BY_NAME, BY_DATE
}