package com.example.strongify.data.model

import com.example.strongify.data.network.model.NetworkCompleteCycle


class CompleteCycle(
    var id: Int,
    var type: String,
    var order: Int,
    var detail: String? = null,
    var name: String,
    var repetitions: Int
) {
    fun asNetworkModel() : NetworkCompleteCycle {
        return NetworkCompleteCycle(
            id = id,
            type = type,
            order = order,
            detail = detail,
            name = name,
            repetitions = repetitions
        )
    }
}