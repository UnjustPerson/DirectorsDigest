package com.govorimo.directorsdigest.repository

import com.govorimo.directorsdigest.persistence.models.Director
import com.govorimo.directorsdigest.persistence.models.Film
import com.govorimo.directorsdigest.util.Resource
import java.lang.Exception

class FakeFailureRepository: BaseMainRepository {

    override suspend fun getDirectors(): Resource<List<Director>> {
        return Resource.Failure(Exception())
    }

    override suspend fun insertDirectors(directors: List<Director>) {
        TODO("Not yet implemented")
    }

    override suspend fun getFilms(): Resource<List<Film>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertFilms(films: List<Film>) {
        TODO("Not yet implemented")
    }
}