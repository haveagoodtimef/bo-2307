package com.fenghongzhang.youbo_2307.ui.mine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fenghongzhang.youbo_2307.base.BaseViewModel

/**
 * 我的 Tab 对应 ViewModel
 */
class MineViewModel : BaseViewModel() {

    private val _title = MutableLiveData("我的")
    val title: LiveData<String> = _title

    fun loadData() {
        launchOnViewModelScope {
            showLoading()
            // TODO: 用户信息等业务
            _title.value = "我的"
            hideLoading()
        }
    }
}
