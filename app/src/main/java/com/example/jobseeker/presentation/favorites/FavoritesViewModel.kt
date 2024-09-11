package com.example.jobseeker.presentation.favorites

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
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
    // TODO - create FavoritesRepository OR use JobRepository?   => private val repository: FavoritesRepository

) : ViewModel(){

    private val _favorites = mutableStateListOf<Job>()
    val favorites: SnapshotStateList<Job> = _favorites

    fun getFavorites(){
        userRepository.getFavoriteIds { favoriteIds ->
            if (favoriteIds != null) {
                jobRepository.getFavorites(favoriteIds){ favorites ->
                    _favorites.addAll(favorites)
                }
            }
        }
    }
}