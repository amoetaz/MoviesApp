package com.moetaz.moviesapp.di

import com.moetaz.data.remote.MoviesRepositoryImp
import com.moetaz.data.remote.MoviesService
import com.moetaz.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideMoviesRepository( moviesRepository :MoviesRepositoryImp) : MoviesRepository

}