package com.example.jobseeker.data.repository

import android.util.Log
import androidx.compose.runtime.currentComposer
import com.example.jobseeker.data.model.Job
import com.example.jobseeker.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class UserRepository @Inject constructor(
    val db: FirebaseFirestore,
    val auth: FirebaseAuth
) {
    fun getCategorizedJobs(callback: (MutableList<String>?) -> Unit){
        getUser { user ->
            val categorizedJobs: MutableList<String>?  = user?.favoriteJobIds
            user?.discardedJobIds?.let {
                categorizedJobs?.addAll(it)
            }
            Log.d("MARKO","categorizedJobs: $categorizedJobs")
            callback(categorizedJobs)
        }
    }

    fun getUser(callback: (User?) -> Unit){
        auth.currentUser?.let { currentUser ->
            db.collection("users")
                .document(currentUser.uid)
                .get()
                .addOnSuccessListener {
                    if(it.exists()){
                        val user = User(
                            name = it.getString("name") ?: "",
                            preferredLocation = it.getString("prefferedLocation") ?: "",
                            email = it.getString("email") ?: "",
                            isEmployer = it.getBoolean("isEmployer") ?: false,
                            favoriteJobIds = it.get("favorites") as? MutableList<String>,
                            discardedJobIds = it.get("discarded") as? MutableList<String>
                        )
                        Log.e("USER", "${user.name}")
                        callback(user)
                    } else {
                        callback(null)
                    }
                }
                .addOnFailureListener{ exception ->
                    Log.e("Firebase", "Error getting user data", exception)
                    callback(null)
                }
        }
    }

    //TODO - maybe make it asynchronous?
     fun addToFavorite(jobId: String){
        updateDocumentArray("favorites", jobId)
    }

    fun addToDiscarded(jobId: String){
        updateDocumentArray("discarded", jobId)
    }

    private fun updateDocumentArray(collectionName: String, jobId: String){
        auth.currentUser?.let {
            db.collection("users")
                .document(it.uid)
                .update(collectionName, FieldValue.arrayUnion(jobId))
                .addOnSuccessListener {
                    Log.d("Firebase", "Update $collectionName successfully")
                }
                .addOnFailureListener{exception ->
                    Log.d("Firebase", "Error updating $collectionName", exception)
                }
        }
    }

    fun getFavoriteIds(callback: (MutableList<String>?) ->Unit){
        Log.e("FAVORITE_IDS","inside of UserRepository.getFavoriteIds()")
        getUser { user ->
            if (user != null) {
                Log.e("FAVORITE_IDS","favoriteIDsSize: ${user.favoriteJobIds?.size}")
                callback(user.favoriteJobIds)
            }
        }
    }
}