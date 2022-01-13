package com.govorimo.directorsdigest.persistence.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "director_table")
data class Director (

    @PrimaryKey
    val id: Long,

    val name: String,

    val biography: String,

    val photo: String,

    )