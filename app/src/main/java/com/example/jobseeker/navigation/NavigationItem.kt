package com.example.jobseeker.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    data object Job : NavigationItem("Home", Icons.Default.Home, "Home")
    //data object Search : NavigationItem("Search", Icons.Default.Search, "Search")
    data object Favorites : NavigationItem("Favorites", Icons.Default.FavoriteBorder, "Favorite")
    data object Profile : NavigationItem("Profile", Icons.Default.Person, "Profile")
    data object NewJob : NavigationItem("NewJob", Icons.Default.Add, "New")

    companion object {
        val items = listOf(Job, Favorites, Profile, NewJob)
    }
}