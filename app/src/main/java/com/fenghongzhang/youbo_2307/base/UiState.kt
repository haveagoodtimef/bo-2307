package com.fenghongzhang.youbo_2307.base

/**
 * UI状态密封类
 * 用于管理界面的各种状态
 */
sealed class UiState<out T> {
    /**
     * 初始状态
     */
    object Initial : UiState<Nothing>()
    
    /**
     * 加载中状态
     */
    object Loading : UiState<Nothing>()
    
    /**
     * 成功状态
     * @param data 成功返回的数据
     */
    data class Success<T>(val data: T) : UiState<T>()
    
    /**
     * 错误状态
     * @param message 错误信息
     * @param exception 异常对象
     */
    data class Error(val message: String, val exception: Exception? = null) : UiState<Nothing>()
    
    /**
     * 空数据状态
     */
    object Empty : UiState<Nothing>()
}

/**
 * 扩展函数：判断是否为加载状态
 */
fun <T> UiState<T>.isLoading(): Boolean = this is UiState.Loading

/**
 * 扩展函数：判断是否为成功状态
 */
fun <T> UiState<T>.isSuccess(): Boolean = this is UiState.Success

/**
 * 扩展函数：判断是否为错误状态
 */
fun <T> UiState<T>.isError(): Boolean = this is UiState.Error

/**
 * 扩展函数：判断是否为空状态
 */
fun <T> UiState<T>.isEmpty(): Boolean = this is UiState.Empty

/**
 * 扩展函数：获取成功数据（如果是Success状态）
 */
fun <T> UiState<T>.dataOrNull(): T? = if (this is UiState.Success) this.data else null

/**
 * 扩展函数：获取错误信息（如果是Error状态）
 */
fun <T> UiState<T>.errorMessageOrNull(): String? = if (this is UiState.Error) this.message else null