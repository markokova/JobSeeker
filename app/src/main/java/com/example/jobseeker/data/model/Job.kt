package com.example.jobseeker.data.model

import java.util.Date

data class Job(
    val title: String,
    val category: String, //TODO - myb ENUM? ENUM = {waiter, barista, physical worker, construction, babysiting, IT services, cook, lecturer, ...}
    val salary: Number,
    val location: String,
    val duration: Int,
    val startingDate: Date,
    val image: String
)
