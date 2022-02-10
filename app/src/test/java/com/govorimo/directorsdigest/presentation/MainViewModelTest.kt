package com.govorimo.directorsdigest.presentation

import com.govorimo.directorsdigest.persistence.models.Director
import com.govorimo.directorsdigest.repository.*
import com.govorimo.directorsdigest.util.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import com.govorimo.directorsdigest.util.Log
import com.govorimo.directorsdigest.util.Resource
import com.govorimo.directorsdigest.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.coroutines.ContinuationInterceptor

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class MainViewModelTest{


    private lateinit var mainViewModel: MainViewModel

    private lateinit var mainRepository : BaseMainRepository


    private val directorRichard = Director(250, "Richard Linklater", "Interesting films about American culture by this guy", "")
    private val directorEmir = Director(251, "Emir Kusturica", "Stories are colorful, painting the Balkan communities and individuals", "")
    private val directorPTA = Director(252, "Paul Thomas Anderson", "Visual and storyline expressive and the same time", "")
    private val directorKrzysztof = Director(253, "Krzysztof Kieślowski", "Deep storylines with seemingly simple happenings, master of drama", "")

    @Before
    fun initViewModel(){
        mainRepository = FakeRepository()
        runTest {
            mainRepository.insertDirectors(listOf(directorEmir, directorKrzysztof, directorPTA, directorRichard))
        }
        mainViewModel = MainViewModel(mainRepository)
    }

    @Test
    fun whenViewModelInitalized_thenIsLoadingIsTrue(){

        val isLoadingValue = mainViewModel.isLoading.getOrAwaitValue()
        assertThat(isLoadingValue).isEqualTo(true)

    }

    @Test
    fun whenResourceSuccess_thenIsLoadingIsFalse(){
        runBlocking {
            mainViewModel.getDirectors()
        }
        val isLoadingValue = mainViewModel.isLoading.getOrAwaitValue()
        assertThat(isLoadingValue).isEqualTo(false)
    }

    @Test
    fun whenResourceSuccess_thenDirectorsLiveDataIsRepositoryDirectorsData(){
        runBlocking {
            mainViewModel.getDirectors()
        }
        val directors = mainViewModel.directors.getOrAwaitValue()
        val directorsRepository = runBlocking{ mainRepository.getDirectors() as Resource.Success }
        assertThat(directors).isEqualTo(directorsRepository.data)

    }

    @Test
    fun whenResourceFailure_thenDirectorsLiveDataIsNull_andIsLoadingIsFalse(){

        // we tested data fetching in repository tests in its respective class MainRepositoryTest
        // here we're testing functions in MainViewModel so it's okay to fetch a Failure response by building a separate fake class
        mainRepository = FakeFailureRepository()
        mainViewModel = MainViewModel(mainRepository)
        runBlocking {
            mainViewModel.getDirectors()
        }

        val directors = mainViewModel.directors.getOrAwaitValue()
        val isLoading = mainViewModel.isLoading.getOrAwaitValue()
        assertThat(directors).isEqualTo(null)
        assertThat(isLoading).isEqualTo(false)

    }



}
