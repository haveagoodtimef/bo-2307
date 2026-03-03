package com.fenghongzhang.youbo_2307.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.fenghongzhang.youbo_2307.base.Event
import com.fenghongzhang.youbo_2307.base.UiState

/**
 * MVVM工具类
 * 提供常用的扩展函数和工具方法
 */

/**
 * 观察UiState变化的扩展函数
 */
fun <T> LiveData<UiState<T>>.observeUiState(
    owner: LifecycleOwner,
    onInitial: (() -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onSuccess: ((T) -> Unit)? = null,
    onError: ((String, Exception?) -> Unit)? = null,
    onEmpty: (() -> Unit)? = null
) {
    observe(owner, Observer { uiState ->
        when (uiState) {
            is UiState.Initial -> onInitial?.invoke()
            is UiState.Loading -> onLoading?.invoke()
            is UiState.Success -> onSuccess?.invoke(uiState.data)
            is UiState.Error -> onError?.invoke(uiState.message, uiState.exception)
            is UiState.Empty -> onEmpty?.invoke()
        }
    })
}

/**
 * 观察Event的扩展函数
 */
fun <T> LiveData<Event<T>>.observeEvent(
    owner: LifecycleOwner,
    onEvent: (T) -> Unit
) {
    observe(owner, Observer { event ->
        event?.getContentIfNotHandled()?.let { value ->
            onEvent(value)
        }
    })
}

/**
 * 将数据转换为Event
 */
fun <T> T.toEvent(): Event<T> = Event(this)

/**
 * 网络请求结果处理扩展函数
 */
inline fun <T> Result<T>.handleResult(
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
): Result<T> {
    if (this.isSuccess) {
        onSuccess(this.getOrNull()!!)
    } else {
        onError(this.exceptionOrNull() ?: Exception("未知错误"))
    }
    return this
}

/**
 * 安全获取LiveData值的扩展函数
 */
fun <T> LiveData<T>.getValueOrNull(): T? {
    return try {
        this.value
    } catch (e: Exception) {
        null
    }
}

/**
 * 判断UiState是否为加载状态
 */
fun <T> UiState<T>.isLoading(): Boolean = this is UiState.Loading

/**
 * 判断UiState是否为成功状态
 */
fun <T> UiState<T>.isSuccess(): Boolean = this is UiState.Success

/**
 * 判断UiState是否为错误状态
 */
fun <T> UiState<T>.isError(): Boolean = this is UiState.Error

/**
 * 判断UiState是否为空状态
 */
fun <T> UiState<T>.isEmpty(): Boolean = this is UiState.Empty

/**
 * 获取成功数据（如果是Success状态）
 */
fun <T> UiState<T>.dataOrNull(): T? = if (this is UiState.Success) this.data else null

/**
 * 获取错误信息（如果是Error状态）
 */
fun <T> UiState<T>.errorMessageOrNull(): String? = if (this is UiState.Error) this.message else null