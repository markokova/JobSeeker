package com.example.jobseeker.presentation.job

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jobseeker.data.model.Job
import com.example.jobseeker.ui.theme.Typography
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@Composable
fun JobDetailsScreen(
    jobViewModel: JobViewModel,
    navController: NavController
){
    var job by remember { mutableStateOf<Job?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    val jobId = navController.currentBackStackEntry?.arguments?.getString("jobId")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (isLoading) {
                Text(
                    text = "Loading job details...",
                    style = Typography.bodyMedium,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            } else if (job != null) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 16.dp),
                    elevation = cardElevation(8.dp),
                    colors = CardColors(Color.White, Color.White, Color.LightGray, Color.White),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = job?.title.orEmpty(),
                            style = Typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp),
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        )
                        Text(
                            text = "Category: ${job?.category.orEmpty()}",
                            style = Typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 8.dp),
                            color = Color.Gray
                        )
                        Text(
                            text = "Location: ${job?.location.orEmpty()}",
                            style = Typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 8.dp),
                            color = Color.Gray
                        )

                        Text(
                            text = "Salary: ${job?.salary} €/h",
                            style = Typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 8.dp),
                            color = Color.Gray
                        )

                        Text(
                            text = "Start Date: ${job?.startingDate?.let { getDate(it) }}",
                            style = Typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 8.dp),
                            color = Color.Gray
                        )

                        Text(
                            text = "End Date: ${job?.endingDate?.let { getDate(it) }}",
                            style = Typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 16.dp),
                            color = Color.Gray
                        )

                        Button(
                            onClick = { /* TODO */ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = ButtonColors(Color(0xFF4389FF), Color.White, Color.Blue, Color.Black)
                        ) {
                            Text(
                                text = "Apply Now",
                                color = Color.White,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            } else {
                Text(
                    text = "Job details not found.",
                    style = Typography.bodyMedium,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Red
                )
            }
        }
    }


    LaunchedEffect(jobId) {
        if (jobId != null) {
            jobViewModel.getJob(jobId) {
                job = it
                isLoading = false
            }
        }
    }
}

private fun getDate(dateWithTime: Date) : String {
     return adjustDate(
         dateWithTime
             .toInstant().
             atZone(ZoneId.systemDefault()).
             toLocalDate()
     )
}
//2024-08-05
private fun adjustDate(date: LocalDate) : String{
    val year = date.toString().take(4)
    val temp = date.toString().take(7)
    val month = temp.takeLast(2)
    val day = date.toString().takeLast(2)

    return "$day.$month.$year"
}
