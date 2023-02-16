package com.example.taskado.local_db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskado.Models.MiniTaskR
import com.example.taskado.Models.TaskR
import com.example.taskado.Models.User
import kotlinx.coroutines.launch
import javax.inject.Inject

class viewModelRepo @Inject constructor(private val repository: Repository)
    :ViewModel() {

    fun getAllUsers():List<User> = repository.getAllUser()

    fun updaeUser(user: User)
    {
        viewModelScope.launch {
            repository.updaeUser(user)
        }
    }

    fun resetUsers()
    {
        viewModelScope.launch {
            repository.resetUsers()
        }
    }

    fun deleteUser(user: User)
    {
        viewModelScope.launch {
            repository.deleteUser(user)
        }
    }
    fun addUser(user: User)
    {
        viewModelScope.launch {
            repository.addUser(user)
        }
    }

    // TaskRDao

    fun addTask(task: TaskR) {
        viewModelScope.launch {
            repository.addTask(task)
        }
    }

    fun insertTasks(tasks: List<TaskR>) {
        viewModelScope.launch {
            repository.insertTasks(tasks)
        }
    }

    fun deleteTasks() {
        viewModelScope.launch {
            repository.deleteTasks()
        }
    }

    fun updateTask(task: TaskR) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun getAllTasks(task: List<TaskR>) {
        viewModelScope.launch {
            repository.getAllTasks()
        }
    }

    fun getTaskR(ParentId: String) {
        viewModelScope.launch {
            repository.getTaskR(ParentId)
        }
    }

    fun getFavoriteTaskR(ParentId: Int) {
        viewModelScope.launch {
            repository.getFavoriteTaskR(ParentId)
        }
    }


    //MiniTaskRDao

    fun updateMiniTaskR(task: MiniTaskR) {
        viewModelScope.launch {
            repository.updateMiniTaskR(task)
        }
    }

    fun addMiniTaskR(task: MiniTaskR) {
        viewModelScope.launch {
            repository.addMiniTaskR(task)
        }
    }
    fun insertMiniTasksR(tasks: List<MiniTaskR>) {
        viewModelScope.launch {
            repository.insertMiniTasksR(tasks)
        }
    }

    fun deleteMiniTasksR() {
        viewModelScope.launch {
            repository.deleteMiniTasksR()
        }
    }


    fun getAllTMiniTasks() {
        viewModelScope.launch {
            repository.getAllTMiniTasks()
        }
    }

    fun getAllMiniTasksRbyId(ParentId: String) {
        viewModelScope.launch {
            repository.getAllMiniTasksRbyId(ParentId)
        }
    }


}