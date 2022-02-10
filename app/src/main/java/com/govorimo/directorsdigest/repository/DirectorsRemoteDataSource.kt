package com.govorimo.directorsdigest.repository

import android.util.Log
import com.govorimo.directorsdigest.network.MainApiService
import com.govorimo.directorsdigest.network.models.toDirector
import com.govorimo.directorsdigest.network.models.toFilm
import com.govorimo.directorsdigest.persistence.DirectorsDao
import com.govorimo.directorsdigest.persistence.models.Director
import com.govorimo.directorsdigest.persistence.models.Film
import com.govorimo.directorsdigest.util.Resource
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
class DirectorsRemoteDataSource @Inject constructor(
    private val mainApiService: MainApiService,
    @Named("auth_token") private var token: String
): DirectorsDataSource {

    private val ioDispatcher: CoroutineDispatcher = IO

    override suspend fun getDirectors(): Resource<List<Director>> {
        Log.d("RemoteDataSource", "token: $token")
        return withContext(ioDispatcher) {
            when (val response = withContext(ioDispatcher){
                try{
                    Resource.Success(mainApiService.getDirectorResponse(token))
                }
                catch(exception: Exception){
                    Resource.Failure(exception)
                }
            }) {
                is Resource.Success -> {
                    if (response.data != null) {
                        Log.d("RemoteDataSource", "getDirectors: ${response.data.results}")
                        Resource.Success(response.data.results.map{ it.toDirector() })
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

    override suspend fun insertDirectors(directors: List<Director>) {
        // doesn't need an implementation here
    }

    override suspend fun getFilms(): Resource<List<Film>> {
        Log.d("RemoteDataSource", "token: $token")
        return withContext(ioDispatcher) {
            when (val response = withContext(ioDispatcher){
                try{
                    Resource.Success(mainApiService.getFilmResponse(token))
                }
                catch(exception: Exception){
                    Resource.Failure(exception)
                }
            }) {
                is Resource.Success -> {
                    if (response.data != null) {
                        Log.d("RemoteDataSource", "getDirectors: ${response.data.results}")
                        Resource.Success(response.data.results.map{ it.toFilm() })
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

    override suspend fun insertFilms(films: List<Film>) {
        // doesn't need an implementation here
    }




}
