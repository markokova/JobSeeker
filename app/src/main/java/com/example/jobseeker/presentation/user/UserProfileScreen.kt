package com.example.jobseeker.presentation.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jobseeker.Destinations
import com.example.jobseeker.presentation.login.TextButton

@Composable
fun UserProfileScreen(
    viewModel: UserViewModel,
    navController: NavController
) {
    val user = viewModel.user

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        TextButton(
            onClick = {
                viewModel.signOut()
                navController.navigate(Destinations.Login.name){
                    popUpTo(Destinations.Profile.name) { inclusive = true }
                }
                      },
            modifier = Modifier
                .align(Alignment.End)
        ) {
            Text(
                text = "Sign out",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF4389FF),
                modifier = Modifier.padding(end = 8.dp)
            )
        }

        user.value?.let { userData ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardColors(Color.White, Color.White, Color.LightGray, Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IconTextRow(
                        icon = Icons.Default.Person,
                        text = userData.name
                    )

                    IconTextRow(
                        icon = Icons.Default.Email,
                        text = "E-mail: ${userData.email}"
                    )

                    IconTextRow(
                        icon = Icons.Default.LocationOn,
                        text = "Preferred Location: ${userData.preferredLocation}"
                    )

                    Text(
                        text = "Is Employer: ${if (userData.isEmployer) "Yes" else "No"}",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 16.dp),
                        color = Color.Gray
                    )

                }
            }
        }
    }

    // Fetch user data when the screen is displayed
    LaunchedEffect(Unit) {
        viewModel.getUserData()
    }
}

@Composable
fun IconTextRow(icon: ImageVector, text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF4389FF)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp),
            color = Color.Gray
        )
    }
}
