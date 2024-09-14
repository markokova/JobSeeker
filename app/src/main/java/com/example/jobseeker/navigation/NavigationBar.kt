package com.example.jobseeker.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jobseeker.Destinations

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jobseeker.presentation.user.UserViewModel

@Composable
fun BottomNavigationBar(
    navController: NavController,
    viewModel: UserViewModel
){
    BottomNavigation (
        backgroundColor = Color.White,
        contentColor = Color(0xFF4389FF)
        ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        var isEmployer by remember {mutableStateOf(false)}

        LaunchedEffect(viewModel) {
            viewModel.isEmployer { result ->
                isEmployer = result
            }
        }

        NavigationItem.items.forEach { destination ->
            if(destination.route != "NewJob" || (destination.route == "NewJob" && isEmployer)){
                BottomNavigationItem(
                    selected = currentRoute == destination.route,
                    onClick = {
                        navController.navigate(destination.route)
                    },
                    icon = { Icon(destination.icon, contentDescription = null) },
                    label = { Text(destination.label) }
                )
            }
        }
    }
}

@Composable
fun AddItem(){

}