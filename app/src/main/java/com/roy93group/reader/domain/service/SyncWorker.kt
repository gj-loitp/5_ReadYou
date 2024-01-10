package com.roy93group.reader.domain.service

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.roy93group.reader.infrastructure.pref.SyncIntervalPref
import com.roy93group.reader.infrastructure.pref.SyncOnlyOnWiFiPref
import com.roy93group.reader.infrastructure.pref.SyncOnlyWhenChargingPref
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import java.util.concurrent.TimeUnit

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val accountService: AccountService,
    private val rssService: RssSv,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result =
        withContext(Dispatchers.Default) {
            Log.i("RLog", "doWork: ")
            rssService.get().sync(this@SyncWorker).also {
                rssService.get().clearKeepArchivedArticles()
            }
        }

    companion object {

        private const val IS_SYNCING = "isSyncing"
        const val WORK_NAME = "ReadYou"
        lateinit var uuid: UUID

        fun enqueueOneTimeWork(
            workManager: WorkManager,
        ) {
            workManager.enqueue(
                OneTimeWorkRequestBuilder<SyncWorker>()
                    .addTag(WORK_NAME)
                    .build()
            )
        }

        fun enqueuePeriodicWork(
            workManager: WorkManager,
            syncInterval: SyncIntervalPref,
            syncOnlyWhenCharging: SyncOnlyWhenChargingPref,
            syncOnlyOnWiFi: SyncOnlyOnWiFiPref,
        ) {
            workManager.enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.REPLACE,
                PeriodicWorkRequestBuilder<SyncWorker>(syncInterval.value, TimeUnit.MINUTES)
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiresCharging(syncOnlyWhenCharging.value)
                            .setRequiredNetworkType(if (syncOnlyOnWiFi.value) NetworkType.UNMETERED else NetworkType.CONNECTED)
                            .build()
                    )
                    .addTag(WORK_NAME)
                    .setInitialDelay(syncInterval.value, TimeUnit.MINUTES)
                    .build()
            )
        }

        fun setIsSyncing(boolean: Boolean) = workDataOf(IS_SYNCING to boolean)
        fun Data.getIsSyncing(): Boolean = getBoolean(IS_SYNCING, false)
    }
}
