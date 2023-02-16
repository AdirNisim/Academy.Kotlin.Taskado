package com.example.taskado.local_db

import androidx.room.*
import com.example.taskado.Models.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User);

    @Update
    suspend fun updaeUser(user: User);

    @Delete
    suspend fun deleteUser(user: User);

    @Query("DELETE FROM Users")
    suspend fun resetUsers();

    @Query("SELECT * FROM Users")
    fun getAllUsers(): List<User>
}