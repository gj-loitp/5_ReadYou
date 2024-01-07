package com.roy93group.reader.ui.page.setting.acc

import android.util.Log
import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roy93group.reader.domain.model.account.Account
import com.roy93group.reader.domain.service.AccountService
import com.roy93group.reader.domain.service.OpmlService
import com.roy93group.reader.domain.service.RssService
import com.roy93group.reader.infrastructure.di.DefaultDispatcher
import com.roy93group.reader.infrastructure.di.IODispatcher
import com.roy93group.reader.infrastructure.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountService: AccountService,
    private val rssService: RssService,
    private val opmlService: OpmlService,
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    @MainDispatcher
    private val mainDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _accountUiState = MutableStateFlow(AccountUiState())
    val accountUiState: StateFlow<AccountUiState> = _accountUiState.asStateFlow()
    val accounts = accountService.getAccounts()

    fun initData(accountId: Int) {
        viewModelScope.launch(ioDispatcher) {
            _accountUiState.update { it.copy(selectedAccount = accountService.getAccountById(accountId)) }
        }
    }

    fun update(accountId: Int, block: Account.() -> Unit) {
        viewModelScope.launch(ioDispatcher) {
            accountService.update(accountId, block)
        }
    }

    fun exportAsOPML(accountId: Int, callback: (String) -> Unit = {}) {
        viewModelScope.launch(defaultDispatcher) {
            try {
                callback(opmlService.saveToString(accountId))
            } catch (e: Exception) {
                Log.e("FeedsViewModel", "exportAsOpml: ", e)
            }
        }
    }

    fun hideDeleteDialog() {
        _accountUiState.update { it.copy(deleteDialogVisible = false) }
    }

    fun showDeleteDialog() {
        _accountUiState.update { it.copy(deleteDialogVisible = true) }
    }

    fun showClearDialog() {
        _accountUiState.update { it.copy(clearDialogVisible = true) }
    }

    fun hideClearDialog() {
        _accountUiState.update { it.copy(clearDialogVisible = false) }
    }

    fun delete(accountId: Int, callback: () -> Unit = {}) {
        viewModelScope.launch(ioDispatcher) {
            accountService.delete(accountId)
            withContext(mainDispatcher) {
                callback()
            }
        }
    }

    fun clear(account: Account, callback: () -> Unit = {}) {
        viewModelScope.launch(ioDispatcher) {
            rssService.get(account.type.id).deleteAccountArticles(account.id!!)
            withContext(mainDispatcher) {
                callback()
            }
        }
    }

    fun addAccount(account: Account, callback: (Account?) -> Unit = {}) {
        viewModelScope.launch(ioDispatcher) {
            val addAccount = accountService.addAccount(account)
            try {
                if (rssService.get(addAccount.type.id).validCredentials()) {
                    withContext(mainDispatcher) {
                        callback(addAccount)
                    }
                } else {
                    throw Exception("Unauthorized")
                }
            } catch (e: Exception) {
                accountService.delete(account.id!!)
                withContext(mainDispatcher) {
                    callback(null)
                }
            }
        }
    }

    fun switchAccount(targetAccount: Account, callback: () -> Unit = {}) {
        viewModelScope.launch(ioDispatcher) {
            accountService.switch(targetAccount)
            withContext(mainDispatcher) {
                callback()
            }
        }
    }
}

@Keep
data class AccountUiState(
    val selectedAccount: Flow<Account?> = emptyFlow(),
    val deleteDialogVisible: Boolean = false,
    val clearDialogVisible: Boolean = false,
)
