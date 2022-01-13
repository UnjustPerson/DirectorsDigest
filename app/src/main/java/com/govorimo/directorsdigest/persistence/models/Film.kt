package com.govorimo.directorsdigest.persistence.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "film_table")
data class Film (

    @PrimaryKey
    val id: Long,

    val film_name: String,

    val director: String,

    val year: Int,

    val film_photo: String,

    val film_logo: String?,

    val trailer_link: String?,

    val film_synopsis: String?,

    val screenplay: String?,


): Parcelable