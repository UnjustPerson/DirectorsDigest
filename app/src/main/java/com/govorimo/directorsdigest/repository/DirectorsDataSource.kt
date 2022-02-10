package com.govorimo.directorsdigest.repository

import com.govorimo.directorsdigest.persistence.models.Director
import com.govorimo.directorsdigest.persistence.models.Film
import com.govorimo.directorsdigest.util.Resource

interface DirectorsDataSource {

    suspend fun getDirectors(): Resource<List<Director>>

    suspend fun insertDirectors(directors: List<Director>)

    suspend fun getFilms(): Resource<List<Film>>

    suspend fun insertFilms(films: List<Film>)


}