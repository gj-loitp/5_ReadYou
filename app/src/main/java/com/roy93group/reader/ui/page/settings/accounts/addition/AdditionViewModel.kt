package com.roy93group.reader.ui.page.settings.accounts.addition

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.roy93group.reader.domain.service.OpmlService
import com.roy93group.reader.infrastructure.rss.RssHelper
import com.roy93group.reader.domain.service.RssService
import com.roy93group.reader.infrastructure.android.AndroidStringsHelper
import javax.inject.Inject

@HiltViewModel
class AdditionViewModel @Inject constructor(
    private val opmlService: OpmlService,
    private val rssService: RssService,
    private val rssHelper: RssHelper,
    private val androidStringsHelper: AndroidStringsHelper,
) : ViewModel() {

    private val _additionUiState = MutableStateFlow(AdditionUiState())
    val additionUiState: StateFlow<AdditionUiState> = _additionUiState.asStateFlow()

    fun showAddLocalAccountDialog() {
        _additionUiState.update {
            it.copy(
                addLocalAccountDialogVisible = true,
            )
        }
    }

    fun hideAddLocalAccountDialog() {
        _additionUiState.update {
            it.copy(
                addLocalAccountDialogVisible = false,
            )
        }
    }

    fun showAddFeverAccountDialog() {
        _additionUiState.update {
            it.copy(
                addFeverAccountDialogVisible = true,
            )
        }
    }

    fun hideAddFeverAccountDialog() {
        _additionUiState.update {
            it.copy(
                addFeverAccountDialogVisible = false,
            )
        }
    }
}

data class AdditionUiState(
    val addLocalAccountDialogVisible: Boolean = false,
    val addFeverAccountDialogVisible: Boolean = false,
)
