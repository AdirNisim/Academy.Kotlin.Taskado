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
class UserPageViewModel @Inject constructor(private val authRepository: AuthRepository, private val tasksRepository: TasksRepository) : ViewModel() {

    private val instance = tasksRepository
    var currentUser = getUser()
    var organization = currentUser.user_organization.toString()
    val _eventsStatus : MutableLiveData<Resource<List<Task>>> = MutableLiveData()
    val eventsStatus : LiveData<Resource<List<Task>>> = _eventsStatus

    init {
        viewModelScope.launch {
            instance.getAllTasksLiveData(_eventsStatus,organization)
        }
    }
    fun setAllTasksInRoom(Tasks: List<Task>) {
        authRepository.setTasksR(Tasks)
    }

    fun CleanAllTasksInRooam() {
     authRepository.RemoveAllTaskR()
    }

    suspend fun signOut() {
        viewModelScope.launch {
            authRepository.logOut()
        }
    }

    fun getUser(): User {
        return authRepository.getUser()
    }

    fun deleteTask(chosenTask: Task) {
        viewModelScope.launch {
            tasksRepository.deleteTask(chosenTask,organization)
        }
    }

    fun UpdateTask(chosenTask: Task) {
        viewModelScope.launch {
            tasksRepository.UpdateTask(chosenTask,organization)
        }
    }


}