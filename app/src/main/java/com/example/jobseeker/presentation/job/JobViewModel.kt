package com.example.jobseeker.presentation.job

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.jobseeker.data.model.Job
import com.example.jobseeker.data.repository.JobRepository
import dagger.assisted.AssistedInject

class JobViewModel @AssistedInject constructor(
    private val repository: JobRepository
) : ViewModel() {

    private val _jobs = mutableStateListOf<Job>()
    val jobs: SnapshotStateList<Job> = _jobs

    private val _currentJob = mutableStateOf<Job?>(null)
    val currentJob: State<Job?> = _currentJob

    private var currentIndex = 0

    fun loadInitialJobs() {
        repository.getJobs { newJobs ->
            _jobs.addAll(newJobs)
            if (_jobs.isNotEmpty()) {
                _currentJob.value = _jobs[currentIndex]
            }
        }
    }

    fun swipeLeft() {
        advanceJob()
        //TODO - add decline logic
    }

    fun swipeRight() {
        advanceJob()
        //TODO - add adding to favourites logic logic
    }

    private fun advanceJob() {
        currentIndex++
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
        repository.getJobs { newJobs ->
            _jobs.addAll(newJobs)
        }
    }

}