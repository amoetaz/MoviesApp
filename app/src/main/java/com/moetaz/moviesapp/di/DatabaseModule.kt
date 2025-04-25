package com.moetaz.moviesapp.di

import android.content.Context
import com.moetaz.data.local.AppRoomDatabase
import com.moetaz.data.local.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {


    @Singleton
    @Provides
    fun provideCategoriesDao(@ApplicationContext context: Context): MoviesDao {
        val database = AppRoomDatabase.getDatabase(context)
        return database.moviesDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppRoomDatabase {
        return AppRoomDatabase.getDatabase(appContext)
    }


}