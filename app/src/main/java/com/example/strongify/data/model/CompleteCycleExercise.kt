package com.example.strongify.data.model

import com.example.strongify.data.network.model.NetworkCompleteCycleExercise


class CompleteCycleExercise (
    var exercise: FullExercise,
    var repetitions: Int? = null,
    var order: Int,
    var metadata: String? = null,
    var duration: Int? = null
) {
    fun asNetworkModel() : NetworkCompleteCycleExercise {
        return NetworkCompleteCycleExercise(
            repetitions = repetitions,
            order = order,
            exercise = exercise.asNetworkModel(),
            duration = duration,
            metadata = metadata
        )
    }
}