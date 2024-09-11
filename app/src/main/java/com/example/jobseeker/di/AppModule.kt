package com.example.jobseeker.di

import com.example.jobseeker.data.model.Job
import com.example.jobseeker.data.repository.JobRepository
import com.example.jobseeker.data.repository.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesFirebase(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideJobRepository(
        db: FirebaseFirestore,
        userRepository: UserRepository
    ): JobRepository = JobRepository(db, userRepository)

    @Provides
    @Singleton
    fun provideUserRepository(
        db: FirebaseFirestore,
        auth: FirebaseAuth
    ): UserRepository = UserRepository(db, auth)


}