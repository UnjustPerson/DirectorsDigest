package com.govorimo.directorsdigest.repository

import android.util.Log
import com.govorimo.directorsdigest.persistence.models.Director
import com.govorimo.directorsdigest.persistence.models.Film
import com.govorimo.directorsdigest.network.MainApiService
import com.govorimo.directorsdigest.network.models.toDirector
import com.govorimo.directorsdigest.network.models.toFilm
import com.govorimo.directorsdigest.persistence.DirectorsDao
import com.govorimo.directorsdigest.persistence.FilmsDao
import com.govorimo.directorsdigest.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class MainRepository(private val remoteDataSource: DirectorsDataSource,
                     private val localDataSource: DirectorsDataSource,
                     private val ioDispatcher: CoroutineDispatcher = IO) : BaseMainRepository {


    override suspend fun getDirectors(): Resource<List<Director>> {
        val localDirectors = localDataSource.getDirectors()
        if(shouldFetchDirectors()){
            val remoteDirectors = remoteDataSource.getDirectors()
            return when(remoteDirectors){
                is Resource.Success -> {
                    if (!remoteDirectors.data.isNullOrEmpty())
                        withContext(ioDispatcher){
                            insertDirectors(remoteDirectors.data)
                        }
                    remoteDirectors
                }
                is Resource.Failure -> remoteDirectors
                is Resource.Loading -> remoteDirectors
            }
        }
        else{
            return localDirectors
        }
    }
    // tests
    // if shouldfetchdirectors == true



    override suspend fun insertDirectors(directors: List<Director>) {
        localDataSource.insertDirectors(directors)
    }

    suspend fun shouldFetchDirectors(): Boolean{
        val localDirectors = localDataSource.getDirectors()
        return localDirectors == Resource.Success(emptyList<Director>())
    }



    override suspend fun getFilms(): Resource<List<Film>> {
        val localFilms = localDataSource.getFilms()
        if(localFilms == Resource.Success(emptyList<Film>())){
            val remoteFilms = remoteDataSource.getFilms()
            return when(remoteFilms){
                is Resource.Success -> {
                    if (!remoteFilms.data.isNullOrEmpty())
                        withContext(ioDispatcher){
                            insertFilms(remoteFilms.data)
                        }
                    remoteFilms
                }
                is Resource.Failure -> remoteFilms
                is Resource.Loading -> remoteFilms
            }
        }
        else{
            return localFilms
        }
    }

    override suspend fun insertFilms(films: List<Film>) {
        localDataSource.insertFilms(films)
    }


}