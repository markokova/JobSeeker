package com.example.jobseeker.presentation.job

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jobseeker.Destinations
import com.example.jobseeker.R
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JobScreen(
    viewModel: JobViewModel = hiltViewModel(),
    navController: NavController
) {
    val currentJob = viewModel.currentJob
    val swipeThreshold = 300f
    var accumulatedDrag by remember { mutableFloatStateOf(0f) }
    var isSwiping by remember { mutableStateOf(false) }
    var translationX by remember { mutableFloatStateOf(0f) }

    val animatedTranslationX by animateFloatAsState(
        targetValue = if (isSwiping) translationX else 0f, label = ""
    )
    val animatedAlpha by animateFloatAsState(
        targetValue = if (isSwiping) 1f - (abs(translationX) / 1000f) else 1f, label = ""
    )
    val animatedRotation by animateFloatAsState(
        targetValue = if (isSwiping) (translationX / 30f) else 0f, label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
            .graphicsLayer(
                translationX = animatedTranslationX,
                rotationZ = animatedRotation,
                alpha = animatedAlpha
            )
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onHorizontalDrag = { change, dragAmount ->
                        translationX += dragAmount
                        accumulatedDrag += dragAmount
                        isSwiping = true
                        change.consume()
                    },
                    onDragEnd = {
                        if (abs(accumulatedDrag) > swipeThreshold) {
                            currentJob.value?.let { job ->
                                if (accumulatedDrag > 0) {
                                    viewModel.swipeRight(job.id)
                                } else {
                                    viewModel.swipeLeft(job.id)
                                }
                            }
                        }
                        accumulatedDrag = 0f
                        translationX = 0f
                        isSwiping = false
                    }
                )
            }
            .clickable {
                currentJob.value?.let { job ->
                    navController.navigate(Destinations.JobDetails.getDestinationWithArgs(job.id))
                }
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            if (currentJob.value != null) {
                currentJob.value?.let { job ->
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = getCategoryIcon(job.category)),
                            contentDescription = "Job Category",
                            modifier = Modifier
                                .size(48.dp)
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 16.dp)
                        )

                        Text(
                            text = job.title,
                            style = MaterialTheme.typography.h4,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = job.location,
                            style = MaterialTheme.typography.h6,
                            textAlign = TextAlign.Center,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = "${getDate(job.startingDate)} - ${getDate(job.endingDate)}",
                            style = MaterialTheme.typography.body2,
                            textAlign = TextAlign.Center,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        // Salary Display with Badge
                        Box(
                            modifier = Modifier
                                .background(Color(0xFF4CAF50), shape = RoundedCornerShape(8.dp))
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "${job.salary.toString()} €/h",
                                style = MaterialTheme.typography.h6,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                Text(
                    text = "No jobs available",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadInitialJobs()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getDate(dateWithTime: Date): LocalDate {
    return dateWithTime
        .toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
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
