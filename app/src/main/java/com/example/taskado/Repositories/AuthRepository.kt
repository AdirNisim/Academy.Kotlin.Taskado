package com.example.taskado.Repositories


import com.example.taskado.Models.Task
import com.example.taskado.Models.TaskR
import com.example.taskado.Models.User
import il.co.syntax.finalkotlinproject.utils.Resource

interface AuthRepository {
    suspend fun currentUser(): Resource<User>
    suspend fun login(email: String, password: String): Resource<User>
    suspend fun createUser(newUser: User): Resource<User>
    suspend fun UpdateUser(existingUser: User): Resource<User>
    suspend fun logOut()
    fun setTasksR(data: List<Task>)
    fun RemoveAllTaskR()
    fun getUser(): User
    fun getBuildTaskByR(PartenId:String): Task
    fun getAllFavoriteTasks(): MutableList<TaskR>
    suspend fun updateUserRoom(currentUser: User)
}