package com.example.jobseeker.data.model

data class User(
    val name: String,
    val email: String,
    val isEmployer: Boolean,
    val preferredLocation: String,
    var favoriteJobIds: MutableList<String>?,
    var discardedJobIds: MutableList<String>?
)
