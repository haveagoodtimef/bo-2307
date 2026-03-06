package com.fenghongzhang.youbo_2307.ui.youbo.nearby

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fenghongzhang.youbo_2307.base.BaseViewModel
import com.fenghongzhang.youbo_2307.base.UiState

/**
 * 附近页 ViewModel，仿 MainViewModel
 */
class NearbyViewModel : BaseViewModel() {

    private val _uiState = MutableLiveData<UiState<String>>(UiState.Initial)
    val uiState: LiveData<UiState<String>> = _uiState

    fun loadData() {
        launchOnViewModelScope {
            showLoading()
            _uiState.value = UiState.Loading
            // TODO: 附近列表等业务
            _uiState.value = UiState.Success("附近")
            hideLoading()
        }
    }
}
