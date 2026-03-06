package com.fenghongzhang.youbo_2307.ui.youbo.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fenghongzhang.youbo_2307.base.BaseViewModel
import com.fenghongzhang.youbo_2307.base.UiState

/**
 * 关注页 ViewModel，仿 MainViewModel
 */
class FollowViewModel : BaseViewModel() {

    private val _uiState = MutableLiveData<UiState<String>>(UiState.Initial)
    val uiState: LiveData<UiState<String>> = _uiState

    fun loadData() {
        launchOnViewModelScope {
            showLoading()
            _uiState.value = UiState.Loading
            // TODO: 关注列表等业务
            _uiState.value = UiState.Success("关注")
            hideLoading()
        }
    }
}
