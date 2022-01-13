package com.govorimo.directorsdigest.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.govorimo.directorsdigest.persistence.models.Director
import com.govorimo.directorsdigest.persistence.models.Film


@Dao
interface DirectorsDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDirectors(directors: List<Director>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDirector(director:Director)

    @Query("SELECT * FROM director_table")
    suspend fun getDirectors(): List<Director>


}