package com.example.strongify.data.model

import com.example.strongify.data.network.model.NetworkSport

class Sport(
    var id: Int? = null,
    var name: String,
    var detail: String? = null
) {
    fun asNetworkModel(): NetworkSport {
        return NetworkSport(
            id = id,
            name = name,
            detail = detail
        )
    }
}
