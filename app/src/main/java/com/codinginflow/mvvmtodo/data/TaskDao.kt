package com.codinginflow.mvvmtodo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("select * from task_table where name like '%' || :searchQuery || '%' order by important desc")
    fun getTask(searchQuery: String): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}