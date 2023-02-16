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
class LoginPageViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
private val _userSignInStatus = MutableLiveData<Resource<User>>()
val userSignInStatus : LiveData<Resource<User>> = _userSignInStatus

private val _currentUser = MutableLiveData<Resource<User>>()
val currentUser : LiveData<Resource<User>> = _currentUser

init{
    viewModelScope.launch {
        _currentUser.postValue(Resource.loading())
        _currentUser.postValue(authRepository.currentUser())
    }
}

fun signIn(email:String,password:String){
    _userSignInStatus.postValue(Resource.loading())
    viewModelScope.launch {
        val loginResult = authRepository.login(email,password)
        _userSignInStatus.postValue(loginResult)
    }
}

}