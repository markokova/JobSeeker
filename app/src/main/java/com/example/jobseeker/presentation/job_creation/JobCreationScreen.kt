package com.example.jobseeker.presentation.job_creation

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonColors
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jobseeker.R
import com.example.jobseeker.data.model.Job
import com.example.jobseeker.presentation.job.JobViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

enum class Category() {
    Teaching,
    Cleaning,
    IT,
    Event,
    Engineering,
    Health,
    Catering,
    Sports,
    Other
}

@Composable
fun JobCreationScreen(
    viewModel: JobViewModel
){

    var title by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var salary by remember { mutableStateOf("") }
    var startingDate by remember { mutableStateOf("") }
    var endingDate by remember { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val categories = listOf("Teaching", "Cleaning", "IT", "Event", "Engineering",
                                         "Health", "Catering", "Sports", "Other")

    val isDropDownExpanded = remember { mutableStateOf(false) }
    val itemPosition = remember { mutableIntStateOf(0) }

    val startingDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            startingDate = "$dayOfMonth/${month + 1}/$year"
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    val endingDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            endingDate = "$dayOfMonth/${month + 1}/$year"
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create a New Job",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.Gray
            )

            Divider()

            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Color(0xFF4389FF),
                    focusedIndicatorColor = Color(0xFF4389FF),
                    cursorColor = Color(0xFF4389FF),
                    focusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            TextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Color(0xFF4389FF),
                    focusedIndicatorColor = Color(0xFF4389FF),
                    cursorColor = Color(0xFF4389FF),
                    focusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            TextField(
                value = salary,
                onValueChange = { salary = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Salary (€/h)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Color(0xFF4389FF),
                    focusedIndicatorColor = Color(0xFF4389FF),
                    cursorColor = Color(0xFF4389FF),
                    focusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Color(0xFF4389FF),
                    focusedIndicatorColor = Color(0xFF4389FF),
                    cursorColor = Color(0xFF4389FF),
                    focusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Row(modifier = Modifier.padding(8.dp)) {
                Text(text = "Category:  ")
                Box{
                    Row(
                        modifier = Modifier
                            .clickable {
                                isDropDownExpanded.value = true
                            }
                    ) {
                        Text(text = categories[itemPosition.value])
                        Image(
                            painter = painterResource(id = R.drawable.dropdown_icon),
                            contentDescription = "DropDown Icon"
                        )
                    }
                    DropdownMenu(
                        expanded = isDropDownExpanded.value,
                        onDismissRequest = { isDropDownExpanded.value = false },
                        modifier = Modifier
                            .heightIn(max = 200.dp),
                    ) {
                        categories.forEachIndexed { index, _category ->
                            DropdownMenuItem(
                                text = { Text(text = _category) },
                                onClick = {
                                    isDropDownExpanded.value = false
                                    itemPosition.value = index
                                    category = categories[index]
                                }
                            )
                        }
                    }

                }
            }

            Button(
                onClick = { startingDatePickerDialog.show() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = androidx.compose.material3.ButtonColors(
                    Color(0xFF4389FF),
                    Color.White,
                    Color.Blue,
                    Color.Black
                )
            ) {
                Text(
                    text = if (startingDate.isEmpty()) "Select Starting Date" else startingDate,
                    color = Color.White
                )
            }

            Button(
                onClick = { endingDatePickerDialog.show() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = androidx.compose.material3.ButtonColors(
                    Color(0xFF4389FF),
                    Color.White,
                    Color.Blue,
                    Color.Black
                )

            ) {
                Text(text = endingDate.ifEmpty { "Select Ending Date" }, color = Color.White)
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Button(
                onClick = {
                    if (salary == "" || title == "" || startingDate == "" || location == "") {
                        Toast.makeText(context, "Fill in all fields.", Toast.LENGTH_SHORT).show()
                    } else {
                        val dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy")
                        val parsedStartingDate = LocalDate.parse(startingDate, dateFormatter)
                        val parsedEndingDate = LocalDate.parse(endingDate, dateFormatter)
                        val startingDateAsDate = Date.from(parsedStartingDate.atStartOfDay(calendar.timeZone.toZoneId()).toInstant())
                        val endingDateAsDate = Date.from(parsedEndingDate.atStartOfDay(calendar.timeZone.toZoneId()).toInstant())

                        Log.d("attribute_values","$salary")
                        Log.d("attribute_values","$location")
                        Log.d("attribute_values","$startingDateAsDate")

                        viewModel.createJob(
                            context,
                            Job(
                                id = "",
                                title = title,
                                category = category,
                                salary = salary.toFloat(),
                                location = location,
                                startingDate = startingDateAsDate,
                                endingDate = endingDateAsDate,
                                description = ""
                            ))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = androidx.compose.material3.ButtonColors(
                    Color(0xFF4389FF),
                    Color.White,
                    Color.Blue,
                    Color.Black
                )
            ) {
                Text(text = "Create Job", color = Color.White, fontSize = 16.sp)
            }

        }
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

@Preview
@Composable
fun previewJobCreationScreen() = JobCreationScreen(viewModel = hiltViewModel<JobViewModel>())