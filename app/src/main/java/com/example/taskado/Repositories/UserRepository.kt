package com.example.taskado.local_db

import androidx.lifecycle.LiveData
import com.example.taskado.Models.MiniTaskR
import com.example.taskado.Models.Task
import com.example.taskado.Models.TaskR
import com.example.taskado.Models.User
import javax.inject.Inject


class Repository @Inject constructor(private val userDao: UserDao, private val taskDao: TaskRDao, private val MiniTaskRDao :MiniTaskRDao) {

    // UserDao
    suspend fun addUser(user: User) = userDao.addUser(user)
    suspend fun updaeUser(user: User) = userDao.updaeUser(user)
    suspend fun deleteUser(user: User) = userDao.deleteUser(user)
    suspend fun resetUsers() = userDao.resetUsers()
    fun getAllUser() :List<User> = userDao.getAllUsers()

    //TaskRDao
    suspend fun updateTask(existingTask: TaskR) = taskDao.updateTaskR(existingTask)
    suspend fun deleteTasks() =  taskDao.deleteTasksR()
    suspend fun insertTasks(tasks: List<TaskR>) =  taskDao.insertTasksR(tasks)
    suspend fun addTask(task: TaskR) = taskDao.addTaskR(task)
    fun getAllTasks() : LiveData<List<TaskR>> = taskDao.getAllTasks()
    fun getTaskR(ParentId: String) : TaskR = taskDao.getTaskR(ParentId)
    fun getFavoriteTaskR(parentId: Int): MutableList<TaskR> = taskDao.getFavoriteTaskR(parentId)

    //MiniTaskRDao
    suspend fun updateMiniTaskR(existingTask: MiniTaskR) = MiniTaskRDao.updateMiniTaskR(existingTask)
    suspend fun deleteMiniTasksR() =  MiniTaskRDao.deleteMiniTasksR()
    suspend fun insertMiniTasksR(tasks: List<MiniTaskR>) =  MiniTaskRDao.insertMiniTasksR(tasks)
    suspend fun addMiniTaskR(task: MiniTaskR) = MiniTaskRDao.addMiniTaskR(task)
    fun getAllTMiniTasks() : List<MiniTaskR> = MiniTaskRDao.getAllTMiniTasks()
    fun getAllMiniTasksRbyId(ParentId: String) : List<MiniTaskR> = MiniTaskRDao.getAllMiniTasksRbyId(ParentId)
}