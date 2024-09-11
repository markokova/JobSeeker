package com.example.jobseeker.presentation.job

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.jobseeker.data.model.Job
import com.example.jobseeker.data.repository.JobRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JobViewModel @Inject constructor(
    private val repository: JobRepository
) : ViewModel() {

    private val _jobs = mutableStateListOf<Job>()
    val jobs: SnapshotStateList<Job> = _jobs

    private val _currentJob = mutableStateOf<Job?>(null)
    val currentJob: State<Job?> = _currentJob

    private var currentIndex = 0

    //when state is changed (some employer added a job, and there were no jobs to show anymore before that)
    //then the job should load automatically, index should be
    fun loadInitialJobs() {
        repository.getJobs { newJobs ->
            _jobs.addAll(newJobs)
            if (_jobs.isNotEmpty()) {
                _currentJob.value = _jobs[currentIndex]
            }
            Log.d("JOBS SIZE:","${newJobs.size}")
        }
    }

    fun swipeLeft() {
        advanceJob()
        currentJob.value?.let { repository.addToDiscarded(it.id) }
        Log.d("LEFT", "SWIPE LEFT")
        //TODO - add decline logic
    }

    fun swipeRight() {
        advanceJob()
        currentJob.value?.let { repository.addToFavorite(it.id) }
        Log.d("RIGHT", "SWIPE RIGHT")
        //TODO - add adding to favourites logic
    }

    private fun advanceJob() {
        if(_currentJob.value != null)  {
            currentIndex++
        }
        Log.d("INDEX:", "$currentIndex")
        Log.d("_jobs size:", "${_jobs.size}")
        if (currentIndex < _jobs.size) {
            _currentJob.value = _jobs[currentIndex]
            if (currentIndex >= _jobs.size - 1) {
                prefetchMoreJobs()
            }
        } else {
            _currentJob.value = null
        }
    }

    private fun prefetchMoreJobs() {
        val oldJobs = _jobs
        repository.getJobs { newJobs ->
            if(oldJobs[0].id != newJobs[0].id)
                _jobs.addAll(newJobs)
        }
    }
}