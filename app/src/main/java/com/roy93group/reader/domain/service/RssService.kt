package com.roy93group.reader.domain.service

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import com.roy93group.reader.domain.model.account.AccountType
import com.roy93group.reader.ui.ext.currentAccountType
import javax.inject.Inject

class RssService @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val localRssService: LocalRssService,
    private val feverRssService: FeverRssService,
//    private val googleReaderRssRepository: GoogleReaderRssRepository,
) {

    fun get() = get(context.currentAccountType)

    fun get(accountTypeId: Int) = when (accountTypeId) {
        AccountType.Local.id -> localRssService
        AccountType.Fever.id -> feverRssService
        AccountType.GoogleReader.id -> localRssService
        AccountType.Inoreader.id -> localRssService
        AccountType.Feedly.id -> localRssService
        else -> localRssService
    }
}