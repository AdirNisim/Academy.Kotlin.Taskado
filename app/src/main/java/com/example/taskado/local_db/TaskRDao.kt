package com.example.taskado.local_db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.taskado.Models.TaskR

@Dao
interface TaskRDao {

    @Update
    suspend fun updateTaskR(existingTask: TaskR)

    @Query("DELETE FROM Tasks")
    suspend fun deleteTasksR()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTaskR(Task: TaskR)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasksR(tasks: List<TaskR>)

    @Query("SELECT * FROM Tasks")
    fun getAllTasks() : LiveData<List<TaskR>>

    @Query("SELECT * FROM Tasks WHERE taskId = :parentId")
    fun getTaskR(parentId: String) : TaskR

    @Query("SELECT * FROM Tasks WHERE taskFavorite = :parentId")
    fun getFavoriteTaskR(parentId: Int): MutableList<TaskR>

}