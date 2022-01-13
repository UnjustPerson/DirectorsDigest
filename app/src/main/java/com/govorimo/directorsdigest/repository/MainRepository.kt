package com.govorimo.directorsdigest.repository

import com.govorimo.directorsdigest.persistence.models.Director
import com.govorimo.directorsdigest.persistence.models.Film
import com.govorimo.directorsdigest.network.MainApiService
import com.govorimo.directorsdigest.network.models.toDirector
import com.govorimo.directorsdigest.network.models.toFilm
import com.govorimo.directorsdigest.persistence.DirectorsDao
import com.govorimo.directorsdigest.persistence.FilmsDao
import com.govorimo.directorsdigest.util.Resource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class MainRepository(private val mainApiService: MainApiService,
                     private val directorsDao: DirectorsDao,
                     private val filmsDao: FilmsDao): BaseRepository() {


    suspend fun makeDirectorsCall(token: String): Resource<List<Director>?> {
        val directors = directorsDao.getDirectors()
        if(directors == emptyList<Director>()){
            return withContext(IO) {
                when (val response = safeCall { mainApiService.getDirectorResponse(token) }) {
                    is Resource.Success -> {
                        if (response.data != null) {
                            Resource.Success(response.data.results.map{ it.toDirector() }).also {
                                directorsDao.insertDirectors(response.data.results.map{ it.toDirector()})
                            }
                        } else {
                            Resource.Success(null)
                        }
                    }
                    is Resource.Failure -> {
                        Resource.Failure(response.exception)
                    }
                    else -> Resource.Loading
                }

            }
        }
        else{
            return safeCall { directorsDao.getDirectors() }
        }
    }


    suspend fun makeFilmsCall(token: String): Resource<List<Film>?> {
        val films = filmsDao.getFilms()
        if(films == emptyList<Film>()){
            return withContext(IO) {
                when (val response = safeCall { mainApiService.getFilmResponse(token) }) {
                    is Resource.Success -> {
                        if (response.data != null) {
                            Resource.Success(response.data.results.map{ it.toFilm() }).also {
                                filmsDao.insertFilms(response.data.results.map{ it.toFilm()})
                            }
                        } else {
                            Resource.Success(null)
                        }
                    }
                    is Resource.Failure -> {
                        Resource.Failure(response.exception)
                    }
                    else -> Resource.Loading
                }

            }
        }
        else{
            return safeCall { filmsDao.getFilms() }
        }
    }

}