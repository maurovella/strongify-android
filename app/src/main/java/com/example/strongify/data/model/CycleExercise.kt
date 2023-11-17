package com.example.strongify.data.model

import com.example.strongify.data.network.model.NetworkCycleExercise


data class CycleData(
    val cycleName: String,
    val cycleRepetitions: Int,
    val cycleExercises: List<CompleteCycleExercise>
)

class CycleExercise(
    var orderBy: String,
    var content: List<CompleteCycleExercise>,
    var direction: String,
    var isLastPage: Boolean
) {
    fun asNetworkModel() : NetworkCycleExercise {
        return NetworkCycleExercise(
            isLastPage = isLastPage,
            direction = direction,
            content = content,
            orderBy = orderBy
        )
    }

}