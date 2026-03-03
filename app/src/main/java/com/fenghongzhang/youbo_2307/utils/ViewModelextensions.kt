package com.fenghongzhang.youbo_2307.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * ViewModel 创建约定：
 * - Activity：直接使用 `private val xxxViewModel: XxxViewModel by viewModels()`，并在 initBinding 中赋给基类 viewModel
 * - Fragment：使用 `private val xxxViewModel: XxxViewModel by viewModels()`，并重写 provideViewModel() 返回该实例
 *
 * 当 ViewModel 需要带参构造（如 Repository）时，使用 viewModelFactory 配合 viewModels(factory) 使用。
 */
fun viewModelFactory(
    creator: () -> ViewModel
): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return creator() as T
    }
}
