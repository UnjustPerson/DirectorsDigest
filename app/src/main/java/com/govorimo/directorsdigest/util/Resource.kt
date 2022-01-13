package com.govorimo.directorsdigest.util

sealed class Resource<out T> {

    data class Success<out T>(val data: T?) : Resource<T>()
    data class Failure(val exception: Exception) : Resource<Nothing>()
    object Loading : Resource<Nothing>()

}