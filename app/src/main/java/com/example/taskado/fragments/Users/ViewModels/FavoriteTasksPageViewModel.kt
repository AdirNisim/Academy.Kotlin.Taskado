package com.example.taskado.fragments.Users.ViewModels


import androidx.lifecycle.ViewModel
import com.example.taskado.Models.TaskR
import com.example.taskado.Models.User
import com.example.taskado.Repositories.AuthRepository
import com.example.taskado.Repositories.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteTasksPageViewModel @Inject constructor(private val authRepository: AuthRepository, private val tasksRepository: TasksRepository) : ViewModel() {


    suspend fun getAllFavoitesfromRoom(): MutableList<TaskR> {
        return withContext(Dispatchers.IO) {
            authRepository.getAllFavoriteTasks()
        }
    }

    fun getUser(): User {
        return authRepository.getUser()
    }


}