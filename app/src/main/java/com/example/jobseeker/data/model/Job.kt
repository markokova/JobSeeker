package com.example.jobseeker.data.model

import java.util.Date
//TODO - add employerEmail attribute, so user can apply to job by sending email to employer
data class Job(
    val id: String,
    val title: String,
    val category: String, //TODO - myb ENUM? ENUM = {waiter, barista, physical worker, construction, babysiting, IT services, cook, lecturer, ...}
    val salary: Number,
    val location: String,
    val startingDate: Date,
    val endingDate: Date,
    val description: String
)
