package com.fenghongzhang.youbo_2307.ui.haohuo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fenghongzhang.youbo_2307.base.BaseViewModel

/**
 * 好货 Tab 对应 ViewModel
 */
class HaohuoViewModel : BaseViewModel() {

    private val _title = MutableLiveData("好货")
    val title: LiveData<String> = _title

    fun loadData() {
        launchOnViewModelScope {
            showLoading()
            // TODO: 好货列表等业务
            _title.value = "好货"
            hideLoading()
        }
    }
}
