package com.govorimo.directorsdigest.repository

import com.govorimo.directorsdigest.persistence.models.Director
import com.govorimo.directorsdigest.persistence.models.Film
import com.govorimo.directorsdigest.util.Resource

class FakeRepository: BaseMainRepository {

    var directors = mutableListOf<Director>()


    override suspend fun getDirectors(): Resource<List<Director>> {
        return Resource.Success<List<Director>>(directors)
    }

    override suspend fun insertDirectors(directors: List<Director>) {
        directors.forEach{ this.directors.add(it) }
    }

    override suspend fun getFilms(): Resource<List<Film>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertFilms(films: List<Film>) {
        TODO("Not yet implemented")
    }


}