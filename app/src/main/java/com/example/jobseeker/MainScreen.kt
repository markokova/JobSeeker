package com.example.jobseeker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jobseeker.navigation.BottomNavigationBar
import com.example.jobseeker.presentation.favorites.FavoritesScreen
import com.example.jobseeker.presentation.favorites.FavoritesViewModel
import com.example.jobseeker.presentation.login.LoginScreen
import com.example.jobseeker.presentation.login.LoginViewModel
import com.example.jobseeker.presentation.job.JobScreen
import com.example.jobseeker.presentation.job.JobViewModel
import com.example.jobseeker.presentation.job_creation.JobCreationScreen
import com.example.jobseeker.presentation.search.SearchScreen
import com.example.jobseeker.presentation.user.UserProfileScreen
import com.example.jobseeker.presentation.user.UserViewModel

enum class Destinations() {
    Home,
    Favorites,
    Search,
    Profile,
    NewJob,
    Login,
    JobDetails
}


//TODO - create JobDetails screen, change everything about JobScreen to HomeScreen (Maybe?), to JobDetails screen you can
//navigate from homeScreen by clicking on job card or by favorites screen by doing the same.
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JobSeekerApp(
    navController: NavHostController = rememberNavController(),
    jobViewModel: JobViewModel = hiltViewModel()
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val showBottomBar by remember {
        derivedStateOf {
            when (navBackStackEntry?.destination?.route){
                Destinations.Login.name -> false
                else -> true
            }
        }
    }

    Scaffold(
        bottomBar = {
            if(showBottomBar)
                BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        //TODO - startDestination should depend on users login status, if he is already loged in then it should be Destinations.Job.name
        NavHost(navController = navController, startDestination = Destinations.Login.name, Modifier.padding(innerPadding)){
            composable(Destinations.Login.name){
                val viewModel = hiltViewModel<LoginViewModel>()
                LoginScreen(viewModel = viewModel, navController = navController)
            }
            //TODO - add on button click/on item click za svaki screen, kad se button klikne onda ide navController.navigate(
            composable(Destinations.Home.name){
                JobScreen(viewModel = jobViewModel)
            }

            composable(Destinations.NewJob.name){
                JobCreationScreen(jobViewModel)
            }

            //TODO - use JobViewModel?
            composable(Destinations.Favorites.name){
                val viewModel = hiltViewModel<FavoritesViewModel>()
                FavoritesScreen(viewModel = viewModel)
            }

            //TODO - implement if there is time
            composable(Destinations.Search.name){
                SearchScreen(viewModel = jobViewModel)
            }

            composable(Destinations.Profile.name){
                val viewModel = hiltViewModel<UserViewModel>()
                UserProfileScreen(viewModel)
            }
        }
    }
}