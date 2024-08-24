package com.example.jobseeker.presentation.login

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val context: Context) : ViewModel() {
    private lateinit var auth: FirebaseAuth

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
}