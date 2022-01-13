package com.govorimo.directorsdigest.repository

import com.govorimo.directorsdigest.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <T> safeCall(makeCall: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO){
            try{
                Resource.Success(makeCall.invoke())
            }
            catch(exception: Exception){
                Resource.Failure(exception)
            }
        }
    }

}