package com.roy93group.reader.domain.service

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.withContext
import com.roy93group.reader.R
import com.roy93group.reader.domain.model.general.toVersion
import com.roy93group.reader.infrastructure.di.IODispatcher
import com.roy93group.reader.infrastructure.di.MainDispatcher
import com.roy93group.reader.infrastructure.net.Download
import com.roy93group.reader.infrastructure.net.NetworkDataSource
import com.roy93group.reader.infrastructure.net.downloadToFileWithProgress
import com.roy93group.reader.infrastructure.pref.*
import com.roy93group.reader.infrastructure.pref.NewVersionSizePref.formatSize
import com.roy93group.reader.ui.ext.getCurrentVersion
import com.roy93group.reader.ui.ext.getLatestApk
import com.roy93group.reader.ui.ext.showToast
import com.roy93group.reader.ui.ext.skipVersionNumber
import javax.inject.Inject

class AppService @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val networkDataSource: NetworkDataSource,
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher
    private val mainDispatcher: CoroutineDispatcher,
) {

    suspend fun checkUpdate(showToast: Boolean = true): Boolean? = withContext(ioDispatcher) {
        try {
            val response = networkDataSource.getReleaseLatest(context.getString(R.string.update_link))
            when {
                response.code() == 403 -> {
                    withContext(mainDispatcher) {
                        if (showToast) context.showToast(context.getString(R.string.rate_limit))
                    }
                    return@withContext null
                }

                response.body() == null -> {
                    withContext(mainDispatcher) {
                        if (showToast) context.showToast(context.getString(R.string.check_failure))
                    }
                    return@withContext null
                }
            }
            val skipVersion = context.skipVersionNumber.toVersion()
            val currentVersion = context.getCurrentVersion()
            val latest = response.body()!!
            val latestVersion = latest.tag_name.toVersion()
//            val latestVersion = "1.0.0".toVersion()
            val latestLog = latest.body ?: ""
            val latestPublishDate = latest.published_at ?: latest.created_at ?: ""
            val latestSize = latest.assets?.first()?.size ?: 0
            val latestDownloadUrl = latest.assets?.first()?.browser_download_url ?: ""

            Log.i("RLog", "current version $currentVersion")
            if (latestVersion.whetherNeedUpdate(currentVersion, skipVersion)) {
                Log.i("RLog", "new version $latestVersion")
                NewVersionNumberPref.put(context, this, latestVersion.toString())
                NewVersionLogPref.put(context, this, latestLog)
                NewVersionPublishDatePref.put(context, this, latestPublishDate)
                NewVersionSizePref.put(context, this, latestSize.formatSize())
                NewVersionDownloadUrlPref.put(context, this, latestDownloadUrl)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("RLog", "checkUpdate: ${e.message}")
            withContext(mainDispatcher) {
                if (showToast) context.showToast(context.getString(R.string.check_failure))
            }
            null
        }
    }

    suspend fun downloadFile(url: String): Flow<Download> =
        withContext(ioDispatcher) {
            Log.i("RLog", "downloadFile start: $url")
            try {
                return@withContext networkDataSource.downloadFile(url)
                    .downloadToFileWithProgress(context.getLatestApk())
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("RLog", "downloadFile: ${e.message}")
                withContext(mainDispatcher) {
                    context.showToast(context.getString(R.string.download_failure))
                }
            }
            emptyFlow()
        }
}
