package com.govorimo.directorsdigest.network.responses

import com.google.gson.annotations.SerializedName
import com.govorimo.directorsdigest.network.models.DirectorDTO
import com.govorimo.directorsdigest.network.models.FilmDTO

data class FilmResponse (

    @SerializedName("count")
    var count: Int,

    @SerializedName("results")
    var results: List<FilmDTO>,

    )