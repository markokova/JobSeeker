package com.example.jobseeker.data.model

import java.util.Date
data class Job(
    val id: String,
    val title: String,
    val category: String,
    val salary: Number,
    val location: String,
    val startingDate: Date,
    val endingDate: Date,
    val description: String
)
