package com.example.jobseeker.presentation.job

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JobScreen(viewModel: JobViewModel){
    val jobs = viewModel.jobs
    val currentJob = viewModel.currentJob

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                    .background(Color.LightGray)
                    .pointerInput(Unit) {
//                        detectDragGestures { change, dragAmount ->
//
//                        }
//                        detectHorizontalDragGestures(onHorizontalDrag = { change, dragAmount -> }, onDragEnd = {viewModel.swipeRight()})
                        detectHorizontalDragGestures { change, dragAmount ->
                            Log.d("dragAmount:", "$dragAmount")
                            change.consume()
                            //TODO - fix this behavior, it is not quite accurate, I shouldn't work with raw pointer value
                            //but instead recognize that swipe has happened and call swipe function only once (currently,
                            //sometimes swipe function is called multiple times and sometimes not even once)
                            if (dragAmount > 27 && dragAmount < 30) {
                                viewModel.swipeRight()
                            } else if (dragAmount > -30 && dragAmount < -27) {
                                viewModel.swipeLeft()
                            }
                        }
                    }
            ) {
                if (currentJob.value != null) {
                    currentJob.value?.let { job ->
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = job.title,
                                //modifier = Modifier.align(Alignment.Center),
                                style = MaterialTheme.typography.h4,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = job.location,
                                style = MaterialTheme.typography.h5,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "${getDate(job.startingDate)} - ${getDate(job.endingDate)}",
                                style = MaterialTheme.typography.body2,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "${job.salary.toString()} €/h",
                                style = MaterialTheme.typography.h5,
                                textAlign = TextAlign.Center
                            )
                            /*
                            maybe store 10 images which represent 10 different categories of jobs, and when some job is picked the image is
                            automatically added, so the user doesn't need to add his own image. Because users rarely actually post images
                            of their job adverts. Then image field is not necessary in NewJob screen. Also, what images to use that are not ugly?
                            */
    //                        Image(
    //                            painter = painterResource(id = )
    //                        )
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
private fun getDate(dateWithTime: Date) : LocalDate {
    return dateWithTime
        .toInstant().
        atZone(ZoneId.systemDefault()).
        toLocalDate()
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun previewJobScreen(){
    JobScreen(viewModel = hiltViewModel<JobViewModel>())
}