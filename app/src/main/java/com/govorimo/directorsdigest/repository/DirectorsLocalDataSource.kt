package com.govorimo.directorsdigest.repository

import com.govorimo.directorsdigest.persistence.DirectorsDao
import com.govorimo.directorsdigest.persistence.FilmsDao
import com.govorimo.directorsdigest.persistence.models.Director
import com.govorimo.directorsdigest.persistence.models.Film
import com.govorimo.directorsdigest.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class DirectorsLocalDataSource(
    private val directorsDao: DirectorsDao,
    private val filmsDao: FilmsDao,
    private val ioDispatcher: CoroutineDispatcher = IO,
): DirectorsDataSource {


    override suspend fun getDirectors(): Resource<List<Director>> {
        return withContext(ioDispatcher) {
            return@withContext try {
                Resource.Success(directorsDao.getDirectors())
            } catch (exception: Exception) {
                Resource.Failure(exception)
            }
        }
    }

    override suspend fun insertDirectors(directors: List<Director>) {
        withContext(ioDispatcher){
            directorsDao.insertDirectors(directors)
        }
    }

    override suspend fun getFilms(): Resource<List<Film>> {
        return withContext(ioDispatcher) {
            return@withContext try {
                Resource.Success(filmsDao.getFilms())
            } catch (exception: Exception) {
                Resource.Failure(exception)
            }
        }
    }

    override suspend fun insertFilms(films: List<Film>) {
        withContext(ioDispatcher){
            filmsDao.insertFilms(films)
        }
    }

}