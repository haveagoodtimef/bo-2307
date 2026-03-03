package com.fenghongzhang.youbo_2307.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * MVVM架构中ViewModel的基类
 * 提供通用的功能和扩展点
 */
abstract class BaseViewModel : ViewModel() {
    
    // 加载状态
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    
    // 错误信息
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    
    // 成功消息
    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> = _successMessage
    
    /**
     * 显示加载状态
     */
    protected fun showLoading() {
        _loading.value = true
    }
    
    /**
     * 隐藏加载状态
     */
    protected fun hideLoading() {
        _loading.value = false
    }
    
    /**
     * 显示错误信息
     */
    protected fun showError(message: String) {
        _errorMessage.value = message
        hideLoading()
    }
    
    /**
     * 显示成功信息
     */
    protected fun showSuccess(message: String) {
        _successMessage.value = message
        hideLoading()
    }
    
    /**
     * 在viewModelScope中启动协程
     */
    protected fun launchOnViewModelScope(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(block = block)
    }
    
    /**
     * 清除错误信息
     */
    protected fun clearError() {

    }
    
    /**
     * 清除成功信息
     */
    protected fun clearSuccess() {

    }
}