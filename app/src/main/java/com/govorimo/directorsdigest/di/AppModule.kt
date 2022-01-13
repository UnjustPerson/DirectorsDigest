package com.govorimo.directorsdigest.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.govorimo.directorsdigest.BaseApplication
import com.govorimo.directorsdigest.network.MainApiService
import com.govorimo.directorsdigest.persistence.AppDatabase
import com.govorimo.directorsdigest.persistence.DirectorsDao
import com.govorimo.directorsdigest.persistence.FilmsDao
import com.govorimo.directorsdigest.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication{
        return app as BaseApplication
    }


    @Singleton
    @Provides
    fun provideAppDb(@ApplicationContext context: Context) = Room.databaseBuilder(context, AppDatabase::class.java, "digest_database")
        .fallbackToDestructiveMigration()
        .build()


    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }


    @Singleton
    @Provides
    fun provideDirectorDao(db: AppDatabase): DirectorsDao {
        return db.directorsDao()
    }


    @Singleton
    @Provides
    fun provideFilmsDao(db: AppDatabase): FilmsDao {
        return db.filmsDao()
    }

    @Singleton
    @Provides
    fun provideOkClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gsonBuilder: Gson, okClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder().baseUrl("https://govorimo.com/")
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .client(okClient)
    }


    @Singleton
    @Provides
    fun provideMainApiService(okClient: OkHttpClient): MainApiService {
        return provideRetrofitBuilder(provideGsonBuilder(), okClient).build().create(MainApiService::class.java)
    }



    @Singleton
    @Provides
    fun provideDirectorRepository(mainApiService: MainApiService, directorsDao: DirectorsDao, filmsDao: FilmsDao): MainRepository {
        return MainRepository(mainApiService = mainApiService, directorsDao = directorsDao, filmsDao = filmsDao)
    }



    @Singleton
    @Provides
    @Named("auth_token")
    fun provideAuthToken(): String{
        return "Token fa0d919aa8712373d81657e012845073033129a2"
    }


}