package com.fenghongzhang.youbo_2307.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

/**
 * MVVM 架构中 Fragment 的基类
 *
 * ViewModel 创建方式二选一：
 * 1. 推荐：使用 [androidx.fragment.app.viewModels] 委托，并在 [provideViewModel] 中返回该实例
 *    ```kotlin
 *    private val listViewModel: ListViewModel by viewModels()
 *    override fun provideViewModel(): ListViewModel? = listViewModel
 *    ```
 * 2. 或重写 [getViewModelClass]，由基类通过 ViewModelProvider 创建
 *
 * @param VB ViewBinding 类型
 * @param VM 继承 [BaseViewModel] 的 ViewModel 类型
 */
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

    protected var _binding: VB? = null
    protected val binding get() = _binding!!

    protected lateinit var viewModel: VM

    private var isViewInit = false
    private var isDataLoaded = false

    /** 获取 ViewBinding 实例 */
    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    /**
     * 若使用 by viewModels() 创建 ViewModel，在此返回该实例；否则返回 null，由 [getViewModelClass] 创建
     */
    protected open fun provideViewModel(): VM? = null

    /**
     * ViewModel 的 Class，仅在 [provideViewModel] 返回 null 时使用
     */
    protected open fun getViewModelClass(): Class<VM>? = null

    private fun initViewModel() {
        provideViewModel()?.let { viewModel = it }
            ?: getViewModelClass()?.let { viewModel = ViewModelProvider(this)[it] }
            ?: throw IllegalStateException(
                "Fragment 必须通过 provideViewModel() 或 getViewModelClass() 提供 ViewModel"
            )
    }

    abstract fun initObserver()
    abstract fun initData()
    abstract fun initListener()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initObserver()
        isViewInit = true
        if (!isDataLoaded) lazyLoadData()
        initListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun lazyLoadData() {
        if (isViewInit && !isDataLoaded && isAdded && isVisible && isResumed) {
            initData()
            isDataLoaded = true
        }
    }

    protected fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    protected fun showLongToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    open fun showLoading() {
        (activity as? BaseActivity<*, *>)?.showLoading()
    }

    open fun hideLoading() {
        (activity as? BaseActivity<*, *>)?.hideLoading()
    }

    protected fun showError(message: String) {
        showToast(message)
    }

    protected fun showSuccess(message: String) {
        showToast(message)
    }
}
