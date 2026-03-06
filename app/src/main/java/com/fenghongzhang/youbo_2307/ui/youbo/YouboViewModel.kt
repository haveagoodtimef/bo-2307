package com.fenghongzhang.youbo_2307.ui.youbo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fenghongzhang.youbo_2307.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 有货 Tab 对应 ViewModel
 */
@HiltViewModel
class YouboViewModel @Inject constructor(): BaseViewModel() {

    private val _title = MutableLiveData("有货")
    val title: LiveData<String> = _title

    fun loadData() {
        launchOnViewModelScope {
            showLoading()
            // TODO: 有货列表等业务
            _title.value = "有货"
            hideLoading()
        }
    }
}
