package com.example.jobseeker.data.model

data class User(
    val name: String,
    val preferredLocation: String,
    val email: String,
    val isEmployer: Boolean,
    var favoriteJobIds: MutableList<String>?,
    var discardedJobIds: MutableList<String>?
)
