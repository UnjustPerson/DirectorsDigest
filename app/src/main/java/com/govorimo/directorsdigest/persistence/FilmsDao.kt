package com.govorimo.directorsdigest.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.govorimo.directorsdigest.persistence.models.Film

@Dao
interface FilmsDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFilms(films: List<Film>)

    @Query("SELECT * FROM film_table")
    suspend fun getFilms(): List<Film>

}