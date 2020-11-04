package com.example.albumsearch.di

import android.content.Context
import androidx.room.Room
import com.example.albumsearch.model.database.AlbumDao
import com.example.albumsearch.model.database.AppDatabase
import com.example.albumsearch.model.network.ITunesApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()
    }

    @Singleton
    @Provides
    fun provideITunesAPI(moshi: Moshi): ITunesApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(ITunesApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(ITunesApi::class.java)
    }

}

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "AlbumSearchDatabase"

    @Provides
    fun provideAlbumDto(appDatabase: AppDatabase): AlbumDao {
        return appDatabase.albumDao()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }

}