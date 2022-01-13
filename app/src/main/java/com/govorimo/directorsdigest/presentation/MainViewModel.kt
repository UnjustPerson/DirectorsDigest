package com.govorimo.directorsdigest.presentation

import android.util.Log
import androidx.lifecycle.*
import com.govorimo.directorsdigest.persistence.models.Director
import com.govorimo.directorsdigest.persistence.models.Film
import com.govorimo.directorsdigest.repository.MainRepository
import com.govorimo.directorsdigest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel
@Inject
constructor (private val repository: MainRepository,
             @Named("auth_token") var token: String): ViewModel() {

    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _directors: MutableLiveData<List<Director>?> = MutableLiveData<List<Director>?>()
    val directors: LiveData<List<Director>?> = _directors

    private var _films = MutableLiveData<List<Film>?>()
    val films: LiveData<List<Film>?> = _films

    fun makeDirectorsCall() {
        _isLoading.value = true
        viewModelScope.launch {
            when(val response = repository.makeDirectorsCall(token)){
                is Resource.Success -> {
                    _isLoading.value = false
                    if (response.data != null) {
                        val directors = response.data
                        _directors.value = directors
                    }
                }
                is Resource.Failure -> {
                    _isLoading.value = false
                    _directors.value = null
                }
                is Resource.Loading -> _isLoading.value = true
            }
            Log.d("tebramoi", "${_directors.value}")
        }
    }


    fun makeFilmsCall() {
        _isLoading.value = true
        viewModelScope.launch {
            when(val response = repository.makeFilmsCall(token)){
                is Resource.Success -> {
                    _isLoading.value = false
                    if (response.data != null) {
                        val films = response.data
                        _films.value = films
                    }
                }
                is Resource.Failure -> {
                    _isLoading.value = false
                    _films.value = null
                }
                is Resource.Loading -> _isLoading.value = true
            }
            Log.d("tebramoi", "${_directors.value}")
        }
    }

}




