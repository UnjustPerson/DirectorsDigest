package com.govorimo.directorsdigest.network.models

import com.google.gson.annotations.SerializedName
import com.govorimo.directorsdigest.persistence.models.Film

data class FilmDTO (

    @SerializedName("id")
    val id: Long,

    @SerializedName("film_name")
    val film_name: String,

    @SerializedName("director")
    val director: String,

    @SerializedName("year")
    val year: Int,

    @SerializedName("film_photo")
    val film_photo: String,

    @SerializedName("film_logo")
    val film_logo: String?,

    @SerializedName("trailer_link")
    val trailer_link: String?,

    @SerializedName("film_synopsis")
    val film_synopsis: String?,

    @SerializedName("screenplay")
    val screenplay: String?,

)

fun FilmDTO.toFilm(): Film {
    return Film(
        id = id,
        film_name = film_name,
        director = director,
        year = year,
        film_photo = film_photo,
        film_logo = film_logo,
        trailer_link = trailer_link,
        film_synopsis = film_synopsis,
        screenplay = screenplay,
    )
}