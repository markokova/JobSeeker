package com.example.jobseeker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jobseeker.navigation.BottomNavigationBar
import com.example.jobseeker.presentation.favorites.FavoritesScreen
import com.example.jobseeker.presentation.favorites.FavoritesViewModel
import com.example.jobseeker.presentation.job.JobDetailsScreen
import com.example.jobseeker.presentation.login.LoginScreen
import com.example.jobseeker.presentation.login.LoginViewModel
import com.example.jobseeker.presentation.job.JobScreen
import com.example.jobseeker.presentation.job.JobViewModel
import com.example.jobseeker.presentation.job_creation.JobCreationScreen
import com.example.jobseeker.presentation.login.RegistrationScreen
import com.example.jobseeker.presentation.search.SearchScreen
import com.example.jobseeker.presentation.user.UserProfileScreen
import com.example.jobseeker.presentation.user.UserViewModel

enum class Destinations() {
    Home,
    Favorites,
    //Search,
    Profile,
    NewJob,
    Login,
    Registration,
    JobDetails;

    fun getDestinationWithArgs(vararg args: String): String{
        return when (this){
            JobDetails -> "${this.name}/${args.joinToString("/")}"
            else -> this.name
        }
    }
}


//TODO - create JobDetails screen, change everything about JobScreen to HomeScreen (Maybe?), to JobDetails screen you can
//navigate from homeScreen by clicking on job card or by favorites screen by doing the same.
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JobSeekerApp(
    navController: NavHostController = rememberNavController(),
    jobViewModel: JobViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()

) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val showBottomBar by remember {
        derivedStateOf {
            when (navBackStackEntry?.destination?.route){
                Destinations.Login.name -> false
                Destinations.Registration.name -> false
                else -> true
            }
        }
    }

    Scaffold(
        bottomBar = {
            if(showBottomBar)
                BottomNavigationBar(
                    navController = navController,
                    viewModel = userViewModel
                )
        },
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5)),
            contentAlignment = Alignment.Center
            ){
                Row(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.suitcase_icon),
                        contentDescription = "Job Category",
                        modifier = Modifier
                            .size(48.dp)
                            .padding(end = 8.dp)
                    )
                    Text(
                        text = "JobSeeker",
                        textAlign = TextAlign.Center,
                        color = Color(0xFF4389FF),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }

        }
    ) { innerPadding ->
        //TODO - startDestination should depend on users login status, if he is already loged in then it should be Destinations.Job.name
        NavHost(navController = navController, startDestination = Destinations.Login.name, Modifier.padding(innerPadding)){
            composable(Destinations.Login.name){
                LoginScreen(userViewModel, navController)
            }
            composable(Destinations.Registration.name){
                RegistrationScreen(userViewModel, navController)
            }
            //TODO - add on button click/on item click za svaki screen, kad se button klikne onda ide navController.navigate(
            composable(Destinations.Home.name){
                JobScreen(jobViewModel, navController)
            }

            composable(Destinations.NewJob.name){
                JobCreationScreen(jobViewModel)
            }

            //TODO - use JobViewModel?
            composable(Destinations.Favorites.name){
                val viewModel = hiltViewModel<FavoritesViewModel>()
                FavoritesScreen(viewModel, navController)
            }

//            composable(Destinations.Search.name){
//                SearchScreen(jobViewModel)
//            }

            composable(Destinations.Profile.name){
                UserProfileScreen(userViewModel, navController)
            }

            composable(
                route = "${Destinations.JobDetails.name}/{jobId}",
                arguments = listOf(navArgument("jobId") { type = NavType.StringType})
            ){
                JobDetailsScreen(jobViewModel, navController)
            }
        }
    }
}