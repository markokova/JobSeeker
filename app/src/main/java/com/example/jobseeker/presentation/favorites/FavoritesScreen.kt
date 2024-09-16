package com.example.jobseeker.presentation.favorites

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jobseeker.Destinations
import com.example.jobseeker.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    navController: NavController
){
    val favorites by viewModel.favorites.observeAsState(emptyList())
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Text(
            text = "Favorites",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp),
            color = Color.Gray
        )
        if (favorites.isNullOrEmpty()) {
            Text(
                text = "No favorites",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Gray,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ){
                items(
                    items = favorites,
                    key = {favorite -> favorite.id}
                ){favorite ->
                    Log.d("FAVORITE_ROUTE","${Destinations.JobDetails.getDestinationWithArgs(favorite.id)}")
                    Log.d("FAVORITE_ID", "${favorite.id}")

                    val dismissState = rememberDismissState()

                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                        viewModel.removeFromFavorites(LocalContext.current, favorite.id)
                        Log.d("SWIPED","remove from favorites")
                    }

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        background = {
                            // Background when swiping
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                                    .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                                    .background(Color.LightGray)

                            ) {
                                Text(
                                    text = "Remove",
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .padding(end = 16.dp),
                                    color = Color(0xFFE16D6D),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        },
                        dismissContent = {
                            Card(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 8.dp),
                                onClick = { navController.navigate(Destinations.JobDetails.getDestinationWithArgs(favorite.id)) },
                                colors = CardColors(Color.White, Color.White, Color.LightGray, Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ){
                                    Image(
                                        painter = painterResource(id = getCategoryIcon(favorite.category)),
                                        contentDescription = "Job Category",
                                        modifier = Modifier
                                            .size(48.dp)
                                            .align(Alignment.CenterHorizontally)
                                            .padding(bottom = 16.dp)
                                    )
                                    Text(
                                        text = favorite.title,
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = favorite.location,
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = "${favorite.salary} €/h",
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }


    LaunchedEffect(Unit) {
        viewModel.getFavorites()
    }
}

private fun getCategoryIcon(category: String): Int {
    return when (category) {
        "Teaching" -> R.drawable.book_icon
        "Cleaning" -> R.drawable.cleaning_icon
        "IT" -> R.drawable.laptop_icon
        "Event" -> R.drawable.event_icon
        "Engineering" -> R.drawable.mechanic_icon
        "Health" -> R.drawable.health_icon
        "Catering" -> R.drawable.bar_icon
        "Sports" -> R.drawable.sport_icon
        else -> R.drawable.suitcase_icon
    }
}
