package com.example.jobseeker.data.repository

import android.util.Log
import com.example.jobseeker.data.model.Job
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class JobRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val userRepository: UserRepository
){

    //TODO - I should probably fetch only the jobs user didn't already categorize(put in favorites or discarded)
    fun getJobs(callback: (List<Job>) -> Unit) {
        userRepository.getCategorizedJobs { categorizedJobs ->
        Log.d("CATEGORIZED JOBS:", "${categorizedJobs?.size}")
            val jobQuery = db.collection("jobs")
                .apply {
                    categorizedJobs?.takeIf { it.isNotEmpty() }?.let {
                        whereNotIn(FieldPath.documentId(), categorizedJobs)
                    }
                }
                .limit(10)

            jobQuery.get()
                .addOnSuccessListener { result ->
                    val jobs = result.map { doc ->
                        Log.d("JOB ID:", "${doc.id}")
                        Job(
                            doc.id,
                            doc.getString("Title") ?: "",
                            doc.getString("Description") ?: "",
                            doc.getDouble("Salary") ?: 0.0,
                            doc.getString("Location") ?: "",
                            doc.getDate("Starting Date") ?: Date(),
                            doc.getDate("Ending Date") ?: Date(),
                            doc.getString("Image") ?: ""
                        )}
                    callback(jobs)
                }
                .addOnFailureListener{exception ->
                    Log.d("Firebase", "Error fetching jobs", exception)
                    callback(emptyList())
                }
        }
    }

    fun getFavorites(favoriteIds: MutableList<String>, callback: (MutableList<Job>) -> Unit){
        //Firestore allows a maximum of 10 elements in 'whereIn' query
        if(favoriteIds.isNotEmpty() && favoriteIds.size <= 10){
            val defaultDate = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }

            db.collection("jobs")
                .whereIn(FieldPath.documentId(), favoriteIds)
                .get()
                .addOnSuccessListener { result ->
                    val favorites = result.map { document ->
                            Job(
                                id = document.id,
                                title = document.getString("Title") ?: "",
                                category = document.getString("Category") ?: "",
                                salary = document.getDouble("Salary") ?: 5,
                                location = document.getString("Location") ?: "",
                                startingDate = document.getDate("Starting Date")!!,
                                endingDate = document.getDate("Ending Date")!!,
                                image = ""
                            )
                    }
                    callback(favorites.toMutableList())
                }
        }
    }

    fun addToFavorite(jobId: String){
        userRepository.addToFavorite(jobId)
    }

    fun addToDiscarded(jobId: String){
        userRepository.addToDiscarded(jobId)
    }
}