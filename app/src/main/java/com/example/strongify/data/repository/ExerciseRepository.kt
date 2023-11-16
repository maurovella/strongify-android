package com.example.strongify.data.repository

import com.example.strongify.data.model.Exercise
import com.example.strongify.data.network.ExerciseRemoteDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class ExerciseRepository(
    private val remoteDataSource: ExerciseRemoteDataSource
) {

    private val exerciseMutex = Mutex()

    private var exercises: List<Exercise> = emptyList()

    suspend fun getExercises(refresh: Boolean = false): List<Exercise> {
        var page = 0

        if (refresh || exercises.isEmpty()) {
            this.exercises = emptyList()

            do {
                val result = remoteDataSource.getExercises(page)
                // Thread-safe write to latestNews
                exerciseMutex.withLock {
                    this.exercises = this.exercises.plus(result.content.map { it.asModel() })
                }
                page++
            } while(!result.isLastPage)
        }

        return exerciseMutex.withLock { this.exercises }
    }

    suspend fun getExercise(exerciseId: Int) : Exercise {
        return remoteDataSource.getExercise(exerciseId).asModel()
    }
}