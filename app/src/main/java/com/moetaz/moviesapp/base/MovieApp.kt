package com.moetaz.moviesapp.base

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.moetaz.data.local.MoviesLocalDataSource
import com.moetaz.data.remote.MoviesService
import com.moetaz.data.workmanager.MoviesSyncWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MovieApp : Application() , Configuration.Provider {

    @Inject
    lateinit var workerFactory: MyWorkerFactory

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        scheduleMoviesSyncWorker()
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "movies_sync_channel",
                "Movies Sync",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Notification for syncing movies in the background"
            }

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun scheduleMoviesSyncWorker() {
        val workManager = WorkManager.getInstance(this)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val periodicSync = PeriodicWorkRequestBuilder<MoviesSyncWorker>(24, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "SyncMovies",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicSync
        )

    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()


}

class MyWorkerFactory @Inject constructor(
    private val  moviesService: MoviesService,
    private val localDataSource: MoviesLocalDataSource // Injecting the service into the factory
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): MoviesSyncWorker? {
        return when (workerClassName) {
            MoviesSyncWorker::class.java.name -> {
                MoviesSyncWorker(appContext, workerParameters, moviesService,localDataSource)
            }
            else -> null
        }
    }
}