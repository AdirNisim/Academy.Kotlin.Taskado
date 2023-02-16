package com.example.taskado.local_db

import androidx.room.*
import com.example.taskado.Models.MiniTaskR


@Dao
interface MiniTaskRDao {

    @Update
    suspend fun updateMiniTaskR(existingTask: MiniTaskR)

    @Query("DELETE FROM MiniTasks")
    suspend fun deleteMiniTasksR()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMiniTaskR(Task: MiniTaskR)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMiniTasksR(tasks: List<MiniTaskR>)

    @Query("SELECT * FROM MiniTasks")
    fun getAllTMiniTasks() : List<MiniTaskR>

    @Query("SELECT * FROM MiniTasks WHERE parentTaskId IN (:ParentId)")
    fun getAllMiniTasksRbyId(ParentId: String) : List<MiniTaskR>
}