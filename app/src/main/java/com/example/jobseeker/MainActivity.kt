package com.example.jobseeker

import com.example.jobseeker.presentation.login.LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModelProvider
import com.example.jobseeker.presentation.login.LoginViewModel
import com.example.jobseeker.presentation.job.JobViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var jobViewModel: JobViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        loginViewModel.onClickRegister("example@example.com", "password123")

        setContent {
            JobSeekerApp()
            //LoginScreen(loginViewModel)
        }
    }
}

