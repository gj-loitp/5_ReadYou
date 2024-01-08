package com.roy93group.reader.infrastructure.preference

import com.roy93group.reader.ui.page.setting.acc.AccountViewModel

typealias SyncBlockList = List<String>

object SyncBlockListPref {

    val default: SyncBlockList = emptyList()

    fun put(accountId: Int, viewModel: AccountViewModel, syncBlockList: SyncBlockList) {
        viewModel.update(accountId) { this.syncBlockList = syncBlockList }
    }

    fun of(syncBlockList: String): SyncBlockList {
        return syncBlockList.split("\n")
    }

    fun toString(syncBlockList: SyncBlockList): String = syncBlockList
        .filter { it.isNotBlank() }
        .map { it.trim() }
        .joinToString { "$it\n" }
}
