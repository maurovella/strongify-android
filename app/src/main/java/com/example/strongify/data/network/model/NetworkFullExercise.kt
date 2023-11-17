package com.example.strongify.data.network.model

import com.example.strongify.data.model.FullExercise
import com.google.gson.annotations.SerializedName
import java.util.*

class NetworkFullExercise (

    @SerializedName("id"     ) var id     : Int,
    @SerializedName("name"   ) var name   : String,
    @SerializedName("detail" ) var detail : String? = null,
    @SerializedName("type"   ) var type   : String,
    @SerializedName("date"   ) var date   : Date?    = null,
    @SerializedName("order"  ) var order  : Int

) {
    fun asModel() : FullExercise {
        return FullExercise(
            id = id,
            name = name,
            detail = detail,
            type = type,
            date = date,
            order = order
        )
    }
}