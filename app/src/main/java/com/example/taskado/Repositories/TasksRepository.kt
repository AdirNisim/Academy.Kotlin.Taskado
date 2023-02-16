package com.example.taskado.Repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskado.Models.Task
import il.co.syntax.finalkotlinproject.utils.Resource

interface TasksRepository {
    suspend fun addTask(newTask: Task,Organization: String) : Resource<Void>
    suspend fun deleteTask(newTask: Task, Organization: String)
    fun getTask(task_id: String,Organization: String,callback: (Task?) -> Unit)
    suspend fun UpdateTask(existingTask: Task, Organization: String)
    fun getAllFavoritesTasks(data : MutableLiveData<Resource<List<Task>>>,Organization: String)
    fun getAllTasksLiveData(data : MutableLiveData<Resource<List<Task>>>,Organization: String)


}