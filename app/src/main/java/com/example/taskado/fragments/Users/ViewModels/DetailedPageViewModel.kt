package com.example.taskado.fragments.Users.ViewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskado.Models.Task

import com.example.taskado.Models.User
import com.example.taskado.Repositories.AuthRepository
import com.example.taskado.Repositories.FireBaseAPI.TasksRepositoryFirebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailedPageViewModel @Inject constructor(private val authRepository: AuthRepository, private val tasksRepository: TasksRepositoryFirebase): ViewModel() {
    private val instance = tasksRepository
    var currentUser = getUser()
    var organization = currentUser.user_organization.toString()


    fun getUser(): User {
        return authRepository.getUser()
    }

    fun getTask(ParentId: String): Task {
        //TODO
        return authRepository.getBuildTaskByR(ParentId)
    }
    fun UpdateTask(task: Task) {
        viewModelScope.launch {
            tasksRepository.UpdateTask(task,currentUser.user_organization.toString())
        }
    }
}