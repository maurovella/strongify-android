package com.example.strongify.data.network.model

import com.example.strongify.data.model.Review
import com.google.gson.annotations.SerializedName
import java.util.*


class NetworkShortUser (
    @SerializedName("id"           )
    var id           : Int,
    @SerializedName("username"     )
    var username     : String? = null,
    @SerializedName("gender"       )
    var gender       : String? = null,
    @SerializedName("avatarUrl"    )
    var avatarUrl    : String? = null,
    @SerializedName("date"         )
    var date         : Date?    = null,
    @SerializedName("lastActivity" )
    var lastActivity : Date?    = null
)

class NetworkUserReview (
    @SerializedName("id"     )
    var id     : Int,
    @SerializedName("date"   )
    var date   : Date?    = null,
    @SerializedName("score"  )
    var score  : Int,
    @SerializedName("review" )
    var review : String,
    @SerializedName("user"   )
    var user   : NetworkShortUser
){
    fun asModel(routineId: Int) : Review {
        return Review(
            score,
            routineId,
            review
        )
    }
}