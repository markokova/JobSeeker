package com.example.jobseeker.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.jobseeker.data.model.Job
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.model.Document
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class JobRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val userRepository: UserRepository
){
    fun getJobs(callback: (List<Job>) -> Unit) {
        userRepository.getCategorizedJobs { categorizedJobs ->
            db.collection("jobs")
                .limit(10)
                .get()
                .addOnSuccessListener {result ->
                    val jobs = result.map {doc ->
                        createJobFromDocument(doc)
                    }
                    val filteredJobs = mutableListOf<Job>()
                    jobs.map { job ->
                        if(!categorizedJobs.isNullOrEmpty()){
                            if (!categorizedJobs.contains(job.id)){
                                filteredJobs.add(job)
                            }
                        } else {
                            callback(jobs)
                        }
                    }
                     callback(filteredJobs)
                }
        }
    }

    fun getJob(jobId: String, callback: (Job) -> Unit){
        db.collection("jobs")
            .document(jobId)
            .get()
            .addOnSuccessListener { doc ->
                val job = createJobFromDocument(doc)
                callback(job)
            }
    }

    fun getFavorites(favoriteIds: MutableList<String>, callback: (MutableList<Job>) -> Unit){
        //Firestore allows a maximum of 10 elements in 'whereIn' query
        if(favoriteIds.size in 1..10){
            val defaultDate = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
            Log.d("FAVORITE", "$favoriteIds, isnotempty: ${!favoriteIds.isNullOrEmpty()}, size=${favoriteIds.size}")
            db.collection("jobs")
                .whereIn(FieldPath.documentId(), favoriteIds)
                .get()
                .addOnSuccessListener { result ->
                    val favorites = result.map { doc -> createJobFromDocument(doc) }
                    callback(favorites.toMutableList())
                }
        } else {
            callback(mutableListOf())
        }
    }

    private fun createJobFromDocument(doc: DocumentSnapshot): Job {
        return Job(
            doc.id,
            doc.getString("title") ?: "",
            doc.getString("category") ?: "",
            doc.getDouble("salary") ?: 0.0,
            doc.getString("location") ?: "",
            doc.getDate("startingDate") ?: Date(),
            doc.getDate("endingDate") ?: Date(),
            doc.getString("description") ?: "",
            )
    }

    fun createJob(context: Context, job: Job){
        db.collection("jobs")
            .add(job)
            .addOnSuccessListener {
                Toast.makeText(context, "Job creation successful", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Job creation failed", Toast.LENGTH_SHORT).show()
            }
    }

    fun addToFavorite(jobId: String){
        userRepository.addToFavorite(jobId)
    }

    fun addToDiscarded(jobId: String){
        userRepository.addToDiscarded(jobId)
    }
}