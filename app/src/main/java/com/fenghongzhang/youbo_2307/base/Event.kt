package com.fenghongzhang.youbo_2307.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * 事件包装类，用于处理一次性事件
 * 防止LiveData在配置更改时重复发送事件
 */
open class Event<out T>(private val content: T) {
    
    var hasBeenHandled = false
        private set // 允许外部读取但不能修改
    
    /**
     * 如果事件还没有被处理，则返回内容并标记为已处理
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
    
    /**
     * 返回内容但不改变handled状态
     */
    fun peekContent(): T = content
}

/**
 * 扩展LiveData<Event<T>>的观察方法
 */
fun <T> LiveData<Event<T>>.observeEvent(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, Observer { event ->
        event?.getContentIfNotHandled()?.let { value ->
            observer(value)
        }
    })
}

/**
 * 创建Event对象的便捷方法
 */
fun <T> T.toEvent(): Event<T> = Event(this)