package com.example.jobseeker.data.repository

import com.example.jobseeker.data.model.Job
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date
import javax.inject.Inject

class JobRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
){

    fun getJobs(callback: (List<Job>) -> Unit) {
        db.collection("jobs")
            .limit(10)
            .get()
            .addOnSuccessListener { result ->
                val jobs = result.map { doc ->
                    Job(
                        doc.getString("Title") ?: "",
                        doc.getString("Description") ?: "",
                        doc.getDouble("Salary") ?: 0.0,
                        doc.getString("Location") ?: "",
                        (doc.getDouble("Duration") ?: 0.0).toInt(),
                        doc.getDate("Starting Date") ?: Date(),
                        doc.getString("Image") ?: ""
                    )}
                callback(jobs)
            }
    }



}