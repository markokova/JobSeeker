package com.example.jobseeker.data.repository

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.currentComposer
import androidx.navigation.NavController
import com.example.jobseeker.Destinations
import com.example.jobseeker.data.model.Job
import com.example.jobseeker.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
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
                            preferredLocation = it.getString("preferredLocation") ?: "",
                            email = it.getString("email") ?: "",
                            isEmployer = it.getBoolean("employer") ?: false,
                            favoriteJobIds = it.get("favoriteJobIds") as? MutableList<String>,
                            discardedJobIds = it.get("discardedJobIds") as? MutableList<String>
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

     suspend fun register(context: Context, user: User, password: String): Boolean {
         Log.d("TEST1","${user.email}, $password")
         return if (!user.email.isNullOrEmpty() && password != ""){
             try {
                 val authResult = auth.createUserWithEmailAndPassword(user.email, password).await()
                 val authUser = authResult.user

                 if (authUser != null){
                     addUser(authUser.uid, user)
                     Toast.makeText(
                         context,
                         "Registration successful",
                         Toast.LENGTH_SHORT
                     ).show()
                     true
                 } else {
                     Toast.makeText(
                         context,
                         "Registration failed",
                         Toast.LENGTH_SHORT
                     ).show()
                     false
                 }
             } catch (e: Exception) {
                 Log.e("REGISTRATION_ERROR","Failed registration: ${e.message}")
                 Toast.makeText(context, "Registration failed. Exception", Toast.LENGTH_SHORT).show()
                 false
             }
         } else {
             Toast.makeText(context, "Fill in all boxes.", Toast.LENGTH_SHORT).show()
             false
         }
    }

    private fun addUser(uid: String,user: User){
        db.collection("users")
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                Log.d("USER","User added successfully")
            }
            .addOnFailureListener {
                Log.e("ERROR_ADDING_USER", "Adding user to db failed")
            }
    }

    suspend fun signUp(email: String, password: String, context: Context): Boolean{
        return if(!email.isNullOrEmpty() && !password.isNullOrEmpty()){
            try {
                val authResult = auth.signInWithEmailAndPassword(email, password).await()
                val authUser = authResult.user

                if (authUser != null){
                    Log.d("PROBLEM","UserRepo.signUp=>${authUser}")
                    Toast.makeText(context, "Sign in successful", Toast.LENGTH_SHORT).show()
                    true
                } else {
                    Toast.makeText(context, "Sign in failed", Toast.LENGTH_SHORT).show()
                    false
                }
            } catch (e: Exception) {
                Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                false
            }
        } else {
            Toast.makeText(context, "Fill in all boxes", Toast.LENGTH_SHORT).show()
            false
        }
    }
    fun signOut(){
        auth.signOut()
    }

    fun isEmployer(callback: (Boolean) -> Unit){
        getUser { user ->
            if (user?.isEmployer == true)  callback(true) else callback(false)
        }
    }

    //TODO - maybe make it asynchronous?
     fun addToFavorite(jobId: String){
        updateDocumentArray("favoriteJobIds", jobId)
    }

    fun addToDiscarded(jobId: String){
        updateDocumentArray("discardedJobIds", jobId)
    }

    fun removeFromFavorites(context: Context, jobId: String){
        auth.currentUser?.let { user ->
            db.collection("users")
                .document(user.uid)
                .update("favoriteJobIds", FieldValue.arrayRemove(jobId))
                .addOnSuccessListener {
                    Toast.makeText(context, "Job Removed", Toast.LENGTH_SHORT).show()
                    Log.d("SWIPED","UserRepo.RemoveFromFavorites(),jobId=$jobId")
                }
        }


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