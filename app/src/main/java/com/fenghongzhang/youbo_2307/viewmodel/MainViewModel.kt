package com.fenghongzhang.youbo_2307.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fenghongzhang.youbo_2307.base.BaseViewModel
import com.fenghongzhang.youbo_2307.base.Event
import com.fenghongzhang.youbo_2307.base.UiState
import com.fenghongzhang.youbo_2307.base.toEvent
import com.fenghongzhang.youbo_2307.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 主页面ViewModel示例
 * 展示如何使用BaseViewModel和Repository进行数据处理
 */
@HiltViewModel
class MainViewModel @Inject constructor(val repository: MainRepository): BaseViewModel() {

    // UI状态管理
    private val _uiState = MutableLiveData<UiState<String>>()
    val uiState: LiveData<UiState<String>> = _uiState
    
    // 计数器示例
    private val _counter = MutableLiveData<Int>(0)
    val counter: LiveData<Int> = _counter
    
    /**
     * 加载数据示例
     */
    fun loadData() {
        launchOnViewModelScope {
            showLoading()
            _uiState.value = UiState.Loading
            
            val result = repository.fetchData()
            
            if (result.isSuccess) {
                _uiState.value = UiState.Success(result.getOrNull() ?: "数据加载成功")
                showSuccess("数据加载完成")
            } else {
                _uiState.value = UiState.Error(
                    result.exceptionOrNull()?.message ?: "未知错误",
                    result.exceptionOrNull() as Exception?
                )
                showError(result.exceptionOrNull()?.message ?: "数据加载失败")
            }
            
            hideLoading()
        }
    }
    
    /**
     * 增加计数器
     */
    fun incrementCounter() {
        _counter.value = (_counter.value ?: 0) + 1
    }
    
    /**
     * 减少计数器
     */
    fun decrementCounter() {
        _counter.value = (_counter.value ?: 0) - 1
    }
    
    /**
     * 重置计数器
     */
    fun resetCounter() {
        _counter.value = 0
        showSuccess("计数器已重置")
    }
    
    /**
     * 发送事件示例
     */
    private val _navigationEvent = MutableLiveData<Event<String>>()
    val navigationEvent: LiveData<Event<String>> = _navigationEvent
    
    fun navigateToDetail() {
        _navigationEvent.value = "detail".toEvent()
    }
}