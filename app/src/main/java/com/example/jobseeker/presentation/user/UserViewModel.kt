package com.example.jobseeker.presentation.user

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.jobseeker.data.model.User
import com.example.jobseeker.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    private val _user = mutableStateOf<User?>(null)
    val user : State<User?> = _user

    fun getUserData(){
        repository.getUser { user ->
            _user.value = user
        }
    }
}