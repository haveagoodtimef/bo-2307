package com.fenghongzhang.youbo_2307.ui.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fenghongzhang.youbo_2307.base.BaseViewModel

/**
 * 消息 Tab 对应 ViewModel
 */
class MessageViewModel : BaseViewModel() {

    private val _title = MutableLiveData("消息")
    val title: LiveData<String> = _title

    fun loadData() {
        launchOnViewModelScope {
            showLoading()
            // TODO: 消息列表等业务
            _title.value = "消息"
            hideLoading()
        }
    }
}
