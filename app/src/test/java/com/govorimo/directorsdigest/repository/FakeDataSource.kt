package com.govorimo.directorsdigest.repository

import com.govorimo.directorsdigest.persistence.models.Director
import com.govorimo.directorsdigest.persistence.models.Film
import com.govorimo.directorsdigest.util.Log
import com.govorimo.directorsdigest.util.Resource
import kotlinx.coroutines.withContext

class FakeDataSource(var localDirectors: MutableList<Director>? = mutableListOf(),
                     var remoteDirectors: MutableList<Director>? = mutableListOf(),
                     var films: MutableList<Film>? = mutableListOf()): DirectorsDataSource {

    override suspend fun getDirectors(): Resource<List<Director>> {
        localDirectors?.let {
            if(localDirectors == emptyList<Director>()){
                insertDirectors(remoteDirectors?.toList()!!)
                Log.d("testSource", "i need to be here 1")
                Log.d("testSource", "i need to be here localDirectors 1 $localDirectors")
                Log.d("testSource", "i need to be here remoteDirectors 1 $remoteDirectors ")
            }
            return Resource.Success(ArrayList(it)) }
        return Resource.Failure(
            Exception("Tasks not found")
        )
    }

    override suspend fun insertDirectors(directors: List<Director>) {
        directors.forEach { localDirectors?.add(it) }
    }

    override suspend fun getFilms(): Resource<List<Film>> {
        films?.let { return Resource.Success(ArrayList(it)) }
        return Resource.Failure(
            Exception("Tasks not found")
        )
    }

    override suspend fun insertFilms(films: List<Film>) {
        films.forEach { this.films?.add(it) }
    }



}