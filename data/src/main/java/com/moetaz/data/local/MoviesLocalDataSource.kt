package com.moetaz.data.local

import androidx.annotation.WorkerThread
import com.moetaz.data.local.MovieEntity
import com.moetaz.data.local.MoviesDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoriesLocalDataSource @Inject constructor(private val dao: MoviesDao) {

    val movies: Flow<List<MovieEntity>> = dao.getAllData()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAll(items: List<MovieEntity>) {
        dao.insertAll(items)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        dao.deleteAll()
    }


}