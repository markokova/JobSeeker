package com.example.jobseeker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//Class which Hilt uses in order to create setup necessary for DI
@HiltAndroidApp
class JobSeekerApplication : Application()