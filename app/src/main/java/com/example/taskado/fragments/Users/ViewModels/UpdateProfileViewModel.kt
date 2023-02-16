package com.example.taskado.fragments.Users.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskado.Models.User
import com.example.taskado.Repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import il.co.syntax.finalkotlinproject.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UpdateProfileViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _currentUser = MutableLiveData<Resource<User>>()
    val currentUser : LiveData<Resource<User>> = _currentUser

    init{
        viewModelScope.launch {
            _currentUser.postValue(Resource.loading())
            _currentUser.postValue(authRepository.currentUser())
        }
    }

    fun getUser(): User {
        return authRepository.getUser()
    }

    suspend fun updateExistingUser(newUser:User): Resource<User> {
        return authRepository.UpdateUser(newUser)
    }
}