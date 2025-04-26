package com.moetaz.data.workmanager

import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.moetaz.data.BuildConfig
import com.moetaz.data.R
import com.moetaz.data.local.MovieEntity
import com.moetaz.data.local.MoviesDao
import com.moetaz.data.local.MoviesLocalDataSource
import com.moetaz.data.local.toMovieEntity
import com.moetaz.data.remote.MoviesService
import com.moetaz.data.toMovie
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class MoviesSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val moviesService: MoviesService,
    private val moviesLocalDataSource: MoviesLocalDataSource
) : CoroutineWorker(context, workerParams) {


    override suspend fun getForegroundInfo(): ForegroundInfo {
        val notification = NotificationCompat.Builder(applicationContext, "movies_sync_channel")
            .setContentTitle("Syncing Movies")
            .setContentText("Fetching latest movies in background...")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        return ForegroundInfo(1, notification)
    }


    override suspend fun doWork(): Result {
        Log.d("TAG", "doWork: movies work manager")
        setForeground(getForegroundInfo())

        return try {
            var page = 1
            val allMovies = mutableListOf<MovieEntity>()

            while (true) {
                val response = moviesService.getPagingMovies(BuildConfig.API_KEY, page)
                allMovies += response.results.map { it.toMovie().toMovieEntity() }

                if (page >= response.totalPages) break
                page++
            }

            moviesLocalDataSource.deleteAll()
            moviesLocalDataSource.insertAll(allMovies)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
