package com.govorimo.directorsdigest.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.govorimo.directorsdigest.persistence.models.Director
import com.govorimo.directorsdigest.persistence.models.Film


@Database(entities = [Director::class, Film::class ], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun directorsDao(): DirectorsDao
    abstract fun filmsDao(): FilmsDao

    companion object{
        val DATABASE_NAME: String = "digest_database"
    }


}