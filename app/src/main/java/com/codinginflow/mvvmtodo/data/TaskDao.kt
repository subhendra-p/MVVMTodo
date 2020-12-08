package com.codinginflow.mvvmtodo.data

import androidx.room.*
import com.codinginflow.mvvmtodo.ui.tasks.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    fun getTask(searchQuery: String, sortOrder: SortOrder, hideCompleted: Boolean): Flow<List<Task>> =
            when(sortOrder) {
                SortOrder.BY_NAME -> getTasksBySortedName(searchQuery, hideCompleted)
                SortOrder.BY_DATE -> getTasksBySortedDateCreated(searchQuery, hideCompleted)
            }

    @Query("select * from task_table where (completed != :hideCompleted or completed = 0) and name like '%' || :searchQuery || '%' order by important desc, name")
    fun getTasksBySortedName(searchQuery: String, hideCompleted : Boolean): Flow<List<Task>>

    @Query("select * from task_table where (completed != :hideCompleted or completed = 0) and name like '%' || :searchQuery || '%' order by important desc, created")
    fun getTasksBySortedDateCreated(searchQuery: String, hideCompleted : Boolean): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}