package com.example.jobseeker.presentation.login

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.TextField
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jobseeker.Destinations
import com.example.jobseeker.data.model.User
import com.example.jobseeker.presentation.user.UserViewModel


@Composable
fun LoginScreen(viewModel: UserViewModel, navController: NavController) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }

    val context = LocalContext.current
//    // Observe login status
//    val loginStatus by viewModel.loginStatus.observeAsState()
//
//    // Display Toast message if loginStatus is not null
//    loginStatus?.let { message ->
//        Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
//    }
    val isSignUpSuccessful by viewModel.isSignUpSuccessful

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.Gray
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Color(0xFF4389FF),
                    focusedIndicatorColor = Color(0xFF4389FF),
                    cursorColor = Color(0xFF4389FF),
                    focusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                label = { Text(text = "Password", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Color(0xFF4389FF),
                    focusedIndicatorColor = Color(0xFF4389FF),
                    cursorColor = Color(0xFF4389FF),
                    focusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.signUp(email.text, password.text, context)
                    Log.d("PROBLEM", "$isSignUpSuccessful")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonColors(Color(0xFF4389FF), Color.White, Color.Blue, Color.Black)

            ) {
                Text(text = "Sign In", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = { /* Handle forgot password */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Forgot Password?", color = Color(0xFF4389FF), fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = { navController.navigate(Destinations.Registration.name) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Register", color = Color(0xFF4389FF), fontSize = 16.sp)
            }
        }
    }


    LaunchedEffect(isSignUpSuccessful) {
        if(isSignUpSuccessful){
            navController.navigate(Destinations.Home.name)
            viewModel.resetSignUpSuccessState()
        }
    }
}

@Composable
fun TextButton(onClick: () -> Unit, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        content()
    }

}