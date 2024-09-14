package com.example.jobseeker.presentation.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jobseeker.Destinations
import com.example.jobseeker.data.model.Job
import com.example.jobseeker.data.model.User
import com.example.jobseeker.presentation.user.UserViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegistrationScreen(
    viewModel: UserViewModel,
    navController: NavController
){

    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var preferredLocation by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmployer by remember { mutableStateOf(false) }

    val isRegistrationSuccessful = viewModel.isRegistrationSuccessful

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Registration",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.Gray
            )
            Divider()

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
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

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
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

            TextField(
                value = preferredLocation,
                onValueChange = { preferredLocation = it },
                label = { Text("Preferred Work Location") },
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

            TextField(
                value = password,
                onValueChange = { password = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                label = { Text(text = "Password") },
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

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Employer"
                )
                Checkbox(
                    checked = isEmployer,
                    onCheckedChange = {
                        isEmployer = it
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF4389FF),
                        uncheckedColor = Color.LightGray,
                        checkmarkColor = Color.White
                    )
                )
            }


            Log.d("isEmployer", "$isEmployer")

            Spacer(modifier = Modifier.padding(8.dp))

            Button(
                onClick = {
                    viewModel.register(
                        context,
                        User(
                            name = name,
                            email = email,
                            isEmployer = isEmployer,
                            preferredLocation = preferredLocation,
                            favoriteJobIds = mutableListOf(),
                            discardedJobIds = mutableListOf()
                        ),
                        password
                    )
                    Log.d("REG FAIL","${isRegistrationSuccessful.value}")
                    if (isRegistrationSuccessful.value){
                        navController.navigate(Destinations.Login.name)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonColors(Color(0xFF4389FF), Color.White, Color.Blue, Color.Black)
            ) {
                Text(text = "Register", color = Color.White)
            }

            TextButton(onClick = { navController.navigate(Destinations.Login.name) }) {
                Text(
                    text = "Back to Login",
                    color = Color(0xFF4389FF)
                )
            }

        }
    }
}