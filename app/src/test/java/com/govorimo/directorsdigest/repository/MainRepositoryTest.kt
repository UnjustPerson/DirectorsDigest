package com.govorimo.directorsdigest.repository

import com.govorimo.directorsdigest.persistence.models.Director
import com.govorimo.directorsdigest.util.Resource
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import com.google.common.truth.Truth.assertThat
import com.govorimo.directorsdigest.util.Log
import com.govorimo.directorsdigest.util.MainCoroutineRule
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainRepositoryTest{

    private val directorRichard = Director(250, "Richard Linklater", "Interesting films about American culture by this guy", "")
    private val directorEmir = Director(251, "Emir Kusturica", "Stories are colorful, painting the Balkan communities and individuals", "")
    private val directorPTA = Director(252, "Paul Thomas Anderson", "Visual and storyline expressive and the same time", "")
    private val directorKrzysztof = Director(253, "Krzysztof Kieślowski", "Deep storylines with seemingly simple happenings, master of drama", "")

    private var localDirectors = listOf(directorPTA, directorKrzysztof).sortedBy { it.name }
    private val remoteDirectors = listOf(directorRichard, directorEmir).sortedBy { it.name }

    private lateinit var directorsLocalDataSource: FakeDataSource
    private lateinit var directorsRemoteDataSource: FakeDataSource

    private lateinit var mainRepository: MainRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createRepository() {
        directorsLocalDataSource = FakeDataSource(localDirectors = localDirectors.toMutableList())
        directorsRemoteDataSource = FakeDataSource(remoteDirectors = remoteDirectors.toMutableList())

        // initializing the repository with fakes that contain data
        mainRepository = MainRepository(
            directorsRemoteDataSource, directorsLocalDataSource
        )
    }

    @Test
    fun givenEmptyDataInDataSource_whenGetDirectors_thenSuccessEmptyList() = runTest {

        // given data sources which don't provide data
        val emptyDataSource = FakeDataSource()
        val mainRepository = MainRepository(
            emptyDataSource, emptyDataSource
        )

        // when directors are requested from the main repository
        val directors = mainRepository.getDirectors()

        // then retrieved directors are an empty list of directors
        assertThat(directors).isEqualTo(Resource.Success(emptyList<Director>()))

    }

    @Test
    fun whenGetDirectors_thenRequestsAllDirectorsFromLocalDataSource() = runTest {

        // when directors are requested from the main repository
        val directors = mainRepository.getDirectors() as Resource.Success

        // then directors are loaded from the local data source
        assertThat(directors.data).isEqualTo(localDirectors)

    }

    @Test
    fun givenLocalDataSourceIsEmpty_whenGetDirectors_requestsAllDirectorsFromRemoteDataSource() = runTest {

        // given local data source is empty
        localDirectors = emptyList<Director>()
        val emptyLocalSource = FakeDataSource(localDirectors.toMutableList())
        val mainRepository = MainRepository(
            directorsRemoteDataSource, emptyLocalSource
        )

        // when directors are requested from the main repository
        val directors = mainRepository.getDirectors() as Resource.Success

        // then directors are loaded from the remote data source
        assertThat(directors.data).isEqualTo(remoteDirectors)

    }


    @Test
    fun whenGetDirectorsAndLocalDirectorsIsDefault_AndInsertRemoteDirectors_thenGetDirectorsIsLocalDirectorsPlusRemoteDirectors() = runTest {

        // given local data source has some default List<Director> values
        // getDirectors fethces localDirectors
        var directors = mainRepository.getDirectors() as Resource.Success

        //this asserts that the getDirectors() fetches directors from the localDataSource
        assertThat(directors.data).isEqualTo(directorsLocalDataSource.localDirectors)

        // remoteDirectors are inserted
        mainRepository.insertDirectors(remoteDirectors)

        directors = mainRepository.getDirectors() as Resource.Success

        //this asserts that getDirectors() fetches from the localDataSource but the local data now includes the inital data + data inserted which is remoteDirectors
        assertThat(directors.data).isEqualTo(directorsLocalDataSource.localDirectors)

    }

    @Test
    fun givenEmptyLocalDataSource_whenGetDirectors_thenRequestsAllDirectorsFromRemoteDataSource_andInsertToLocalDirectors() = runTest {

        // given local data source is empty but remote contains data
        localDirectors = emptyList<Director>()
        val emptyLocalSource = FakeDataSource(localDirectors.toMutableList())

        mainRepository = MainRepository(
            directorsRemoteDataSource, emptyLocalSource, Dispatchers.Main
        )

        val directors = mainRepository.getDirectors() as Resource.Success


        //asserts that getDirectors() inserts data into the localDataSource
        assertThat(directors.data).isEqualTo(emptyLocalSource.localDirectors)

    }



}