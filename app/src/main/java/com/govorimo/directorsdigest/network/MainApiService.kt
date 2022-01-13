package com.govorimo.directorsdigest.network

import com.govorimo.directorsdigest.network.responses.DirectorResponse
import com.govorimo.directorsdigest.network.responses.FilmResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface MainApiService {


    @GET("directors")
    suspend fun getDirectorResponse(@Header("Authorization") token: String): DirectorResponse

    @GET("films")
    suspend fun getFilmResponse(@Header("Authorization") token: String): FilmResponse


}