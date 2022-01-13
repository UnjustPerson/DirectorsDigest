package com.govorimo.directorsdigest.network.responses

import com.google.gson.annotations.SerializedName
import com.govorimo.directorsdigest.network.models.DirectorDTO

data class DirectorResponse (

    @SerializedName("count")
    var count: Int,

    @SerializedName("results")
    var results: List<DirectorDTO>,

    )