package com.mckimquyen.reader

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import coil.ImageLoader
import com.mckimquyen.reader.domain.sv.AccountSv
import com.mckimquyen.reader.domain.sv.AppSv
import com.mckimquyen.reader.domain.sv.LocalRssSv
import com.mckimquyen.reader.domain.sv.OpmlSv
import com.mckimquyen.reader.domain.sv.RssSv
import com.mckimquyen.reader.infrastructure.android.AndroidStringsHelper
import com.mckimquyen.reader.infrastructure.android.CrashHandler
import com.mckimquyen.reader.infrastructure.android.NotificationHelper
import com.mckimquyen.reader.infrastructure.db.AndroidDatabase
import com.mckimquyen.reader.infrastructure.di.ApplicationScope
import com.mckimquyen.reader.infrastructure.di.IODispatcher
import com.mckimquyen.reader.infrastructure.net.NetworkDataSource
import com.mckimquyen.reader.infrastructure.rss.OPMLDataSource
import com.mckimquyen.reader.infrastructure.rss.RssHelper
import com.mckimquyen.reader.ui.ext.del
import com.mckimquyen.reader.ui.ext.getLatestApk
import com.mckimquyen.reader.ui.ext.isFdroid
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import javax.inject.Inject

@HiltAndroidApp
class AndroidApp : Application(), Configuration.Provider {

    @Inject
    lateinit var androidDatabase: AndroidDatabase

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var workManager: WorkManager

    @Inject
    lateinit var networkDataSource: NetworkDataSource

    @Inject
    lateinit var OPMLDataSource: OPMLDataSource

    @Inject
    lateinit var rssHelper: RssHelper

    @Inject
    lateinit var notificationHelper: NotificationHelper

    @Inject
    lateinit var appService: AppSv

    @Inject
    lateinit var androidStringsHelper: AndroidStringsHelper

    @Inject
    lateinit var accountService: AccountSv

    @Inject
    lateinit var localRssService: LocalRssSv

    @Inject
    lateinit var opmlService: OpmlSv

    @Inject
    lateinit var rssService: RssSv

    @Inject
    @ApplicationScope
    lateinit var applicationScope: CoroutineScope

    @Inject
    @IODispatcher
    lateinit var ioDispatcher: CoroutineDispatcher

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var imageLoader: ImageLoader

    /**
     * When the application startup.
     *
     * 1. Set the uncaught exception handler
     * 2. Initialize the default account if there is none
     * 3. Synchronize once
     * 4. Check for new version
     */
    override fun onCreate() {
        super.onCreate()
        CrashHandler(this)
        applicationScope.launch {
            accountInit()
            workerInit()
            checkUpdate()
        }
    }

    /**
     * Override the [Configuration.Builder] to provide the [HiltWorkerFactory].
     */
    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()

    private suspend fun accountInit() {
        withContext(ioDispatcher) {
            if (accountService.isNoAccount()) {
                accountService.addDefaultAccount()
            }
        }
    }

    private suspend fun workerInit() {
        rssService.get().doSync(isOnStart = true)
    }

    private suspend fun checkUpdate() {
        if (isFdroid) return
        withContext(ioDispatcher) {
            applicationContext.getLatestApk().let {
                if (it.exists()) it.del()
            }
        }
        appService.checkUpdate(showToast = false)
    }
}
