package com.example.jobseeker.data.model

import java.util.Date

data class Job(
    val id: String,
    val title: String,
    val category: String, //TODO - myb ENUM? ENUM = {waiter, barista, physical worker, construction, babysiting, IT services, cook, lecturer, ...}
    val salary: Number,
    val location: String,
    val startingDate: Date,
    val endingDate: Date,
    val image: String
)
