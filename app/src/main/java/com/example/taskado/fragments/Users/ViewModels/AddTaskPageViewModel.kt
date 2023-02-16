package com.example.taskado.fragments.Users.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskado.Models.Task
import com.example.taskado.Models.User
import com.example.taskado.Repositories.AuthRepository
import com.example.taskado.Repositories.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import il.co.syntax.finalkotlinproject.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskPageViewModel @Inject constructor(private val authRepository: AuthRepository, private val tasksRepository: TasksRepository): ViewModel(){

    private val _addEventStatus = MutableLiveData<Resource<Void>>()
    val addEventStatus: LiveData<Resource<Void>> = _addEventStatus

    fun addTask(newTask: Task,Organization: String){
        viewModelScope.launch {
            _addEventStatus.postValue(Resource.loading())
            _addEventStatus.postValue(tasksRepository.addTask(newTask,Organization))
        }
    }
    fun getUser(): User {
        return authRepository.getUser()
    }
}