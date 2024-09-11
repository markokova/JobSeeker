package com.example.jobseeker.presentation.login

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.jobseeker.Destinations
import com.example.jobseeker.data.repository.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository : UserRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _loginStatus = MutableLiveData<String>()
    val loginStatus: LiveData<String> get() = _loginStatus

    fun onClickRegister(email: String, password: String){
        registerUser(auth, email, password)
    }

    private fun registerUser(auth: FirebaseAuth, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "createUserWithEmail:success")
                    val user = auth.currentUser
                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                }
            }
    }

    fun login(email: String, password: String, navController: NavController){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    _loginStatus.postValue("Login successful: $user")
                    navController.navigate(Destinations.Home.name)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    _loginStatus.postValue("Login failed")
                }
            }
        //return auth.currentUser != null
    }
}