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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", style = MaterialTheme.typography.bodyMedium)

        BasicTextField(
            value = email,
            onValueChange = { email = it },
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(1.dp, Color.Gray)
                ) {
                    if (email.text.isEmpty()) {
                        Text(text = "Email", color = Color.Gray, modifier = Modifier.padding(8.dp))
                    }
                    innerTextField()
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(1.dp, Color.Gray)
                ) {
                    if (password.text.isEmpty()) {
                        Text(text = "Password", color = Color.Gray, modifier = Modifier.padding(8.dp))
                    }
                    innerTextField()
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.onClickRegister("marko@gmail.com", "password123") },
            colors = ButtonDefaults.buttonColors(Color.Blue),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Sign In", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { /* Handle forgot password */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Forgot Password?", color = Color.Blue, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { /* Handle register */ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Register", color = Color.Blue, fontSize = 16.sp)
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

//@Preview
//@Composable
//fun PreviewLoginScreen(){
//    LoginScreen(viewModel = ())
//}