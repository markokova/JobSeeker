package com.example.jobseeker.presentation.user

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobseeker.data.model.User
import com.example.jobseeker.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository,
): ViewModel() {

    private val _user = mutableStateOf<User?>(null)
    val user : State<User?> = _user

    private val _isRegistrationSuccessful = mutableStateOf(false)
    val isRegistrationSuccessful: State<Boolean> = _isRegistrationSuccessful

    private val _isSignUpSuccessful = mutableStateOf(false)
    val isSignUpSuccessful: State<Boolean> = _isSignUpSuccessful

    fun getUserData(){
        repository.getUser { user ->
            _user.value = user
        }
    }
    fun isEmployer(callback: (Boolean) -> Unit){
        repository.isEmployer {
            callback(it)
        }
    }

    fun signUp(email: String, password: String, context: Context){
        viewModelScope.launch {
            _isSignUpSuccessful.value = repository.signUp(email, password, context)
        }
    }
    fun signOut(){
        repository.signOut()
    }

    fun register(context: Context, user: User, password: String){
        viewModelScope.launch {
            _isRegistrationSuccessful.value = repository.register(context, user, password)
        }
    }

    fun resetSignUpSuccessState(){
        _isSignUpSuccessful.value = false
    }
}