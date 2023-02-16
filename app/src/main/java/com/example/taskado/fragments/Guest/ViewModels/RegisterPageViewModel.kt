package com.example.taskado.fragments.Guest.ViewModels

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
class RegisterPageViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    private val _userRegistrationStatus = MutableLiveData<Resource<User>>()
    val userRegistrationStatus : LiveData<Resource<User>> = _userRegistrationStatus

    fun createUser(newUser: User){
        _userRegistrationStatus.postValue(Resource.loading())
        viewModelScope.launch {
            val registrationResult = repository.createUser(newUser)
            _userRegistrationStatus.postValue(registrationResult)
        }
    }
}