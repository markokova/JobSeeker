package com.example.jobseeker

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jobseeker.presentation.login.LoginScreen
import com.example.jobseeker.presentation.login.LoginViewModel
import com.example.jobseeker.presentation.job.JobScreen
import com.example.jobseeker.presentation.job.JobViewModel

enum class Destinations() {
    Job,
    Favorites,
    Search,
    Profile,
    NewJob,
    Login
}

@Composable
fun JobSeekerApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {

        }
    ) { innerPadding ->
        //TODO - startDestination should depend on users login status, if he is already loged in then it should be Destinations.Job.name
        NavHost(navController = navController, startDestination = Destinations.Login.name, Modifier.padding(innerPadding)){
            composable(Destinations.Login.name){
                val viewModel = hiltViewModel<LoginViewModel>()
                LoginScreen(viewModel = viewModel)
            }
            //TODO - add on button click/on item click za svaki screen, kad se button klikne onda ide navController.navigate(
            composable(Destinations.Job.name){
                val viewModel = hiltViewModel<JobViewModel>()
                JobScreen(viewModel = viewModel)
            }

            composable(Destinations.Favorites.name){

            }

            composable(Destinations.Search.name){

            }

            composable(Destinations.Profile.name){

            }

            composable(Destinations.NewJob.name){

            }
        }

    }
}