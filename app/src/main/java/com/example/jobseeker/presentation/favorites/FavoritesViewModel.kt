package com.example.jobseeker.presentation.favorites

import android.content.Context
import android.util.Log
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jobseeker.data.model.Job
import com.example.jobseeker.data.repository.JobRepository
import com.example.jobseeker.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val jobRepository: JobRepository,
    private val userRepository: UserRepository
) : ViewModel(){

//    private val _favorites = mutableStateListOf<Job>()
//    val favorites: SnapshotStateList<Job> = _favorites

    private val _favorites = MutableLiveData<List<Job>>()
    val favorites: LiveData<List<Job>> get() = _favorites

    fun getFavorites(){
        userRepository.getFavoriteIds { favoriteIds ->
            if (favoriteIds != null) {
                jobRepository.getFavorites(favoriteIds){ favorites ->
                    _favorites.value = favorites
                }
            }
        }
    }

    fun removeFromFavorites(context: Context, jobId: String){
        Log.d("JOB_ID","viewModel.removeFromFavorites: jobId = $jobId")
        userRepository.removeFromFavorites(context, jobId)

        val updatedFavorites = _favorites.value?.toMutableList()?.apply {
            removeAll { it.id == jobId }
        } ?: mutableListOf()

        _favorites.value = updatedFavorites
    }
}