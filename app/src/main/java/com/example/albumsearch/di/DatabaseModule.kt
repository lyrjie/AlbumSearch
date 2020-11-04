package com.example.albumsearch.di

import android.content.Context
import androidx.room.Room
import com.example.albumsearch.model.database.AlbumDao
import com.example.albumsearch.model.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

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