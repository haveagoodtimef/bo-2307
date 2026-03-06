package com.fenghongzhang.youbo_2307.base

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeCompat

/**
 * MVVM 架构中 Activity 的基类
 *
 * 约定：
 * - 使用 [androidx.activity.viewModels] 委托创建 ViewModel，例如：
 *   `private val mainViewModel: MainViewModel by viewModels()`
 * - 在 [initBinding] 中将创建的 ViewModel 赋给 [viewModel]，以便基类/子类通过 [viewModel] 统一使用
 *
 * @param VB ViewBinding 类型
 * @param VM 继承 [BaseViewModel] 的 ViewModel 类型
 */
abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {

    /** ViewBinding */
    protected lateinit var binding: VB

    /** ViewModel，由子类在 [initBinding] 中赋值为通过 viewModels() 创建的实例 */
    protected lateinit var viewModel: VM

    /** 获取 ViewBinding 实例 */
    abstract fun getViewBinding(): VB  

    /** 初始化 ViewBinding 与 ViewModel（此处将 by viewModels() 的实例赋给 viewModel） */
    abstract fun initBinding()

    /** 初始化 LiveData 等观察者 */
    abstract fun initObserver()

    /** 初始化数据（如首次加载） */
    abstract fun initData()

    /** 初始化点击等监听 */
    abstract fun initListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        setContentView(binding.root)
        initObserver()
        initData()
        initListener()
    }

    protected fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected fun showLongToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    open fun showLoading() {}
    open fun hideLoading() {}

    protected fun showError(message: String) {
        showToast(message)
    }

    protected fun showSuccess(message: String) {
        showToast(message)
    }
    override fun getResources(): Resources {
        if (Looper.getMainLooper().thread == Thread.currentThread()) {//解决某些手机某些情况下竖屏适配失败的问题
            AutoSizeCompat.autoConvertDensity(
                super.getResources(),
                1080f,
                super.getResources()
                    .getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
            )
        }
        val resources = super.getResources()
        var configuration = resources.getConfiguration()
        if (resources != null && configuration.fontScale != 1.0f) {
            configuration.fontScale = 1.0f
        }
        resources.updateConfiguration(configuration, resources.getDisplayMetrics())
        return resources
    }

    override fun onConfigurationChanged(newConfig: Configuration) {//解决横屏无法适配的问题
        super.onConfigurationChanged(newConfig)
        AutoSize.autoConvertDensity(
            this,
            1080f,
            super.getResources()
                .getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
        )
    }
}
