package com.govorimo.directorsdigest.network.models

import com.google.gson.annotations.SerializedName
import com.govorimo.directorsdigest.persistence.models.Director

data class DirectorDTO (

    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("biography")
    val biography: String,

    @SerializedName("photo")
    val photo: String,


    )

fun DirectorDTO.toDirector(): Director {
    return Director(
        id = id,
        name = name,
        biography = biography,
        photo = photo,
        )
}