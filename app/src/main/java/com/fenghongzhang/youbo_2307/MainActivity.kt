package com.fenghongzhang.youbo_2307

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.fenghongzhang.youbo_2307.base.BaseActivity
import com.fenghongzhang.youbo_2307.base.UiState
import com.fenghongzhang.youbo_2307.base.observeEvent
import com.fenghongzhang.youbo_2307.databinding.ActivityMainBinding
import com.fenghongzhang.youbo_2307.viewmodel.MainViewModel
import com.fenghongzhang.youbo_2307.web.WebCommonActivity
import com.tencent.bugly.crashreport.CrashReport

/**
 * 首页 - MVVM 架构
 * ViewModel 使用 by viewModels() 创建，在 initBinding 中赋给基类 viewModel。
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initBinding() {
        binding = getViewBinding()
        viewModel = mainViewModel
    }
    
    override fun initObserver() {


        // 观察UI状态变化
        viewModel.uiState.observe(this, Observer { uiState ->
            when (uiState) {
                is UiState.Initial -> {
                    binding.tvDataResult.text = "点击按钮加载数据"
                }
                is UiState.Loading -> {
                    binding.tvDataResult.text = "正在加载数据..."
                }
                is UiState.Success -> {
                    binding.tvDataResult.text = uiState.data
                }
                is UiState.Error -> {
                    binding.tvDataResult.text = "错误: ${uiState.message}"
                }
                is UiState.Empty -> {
                    binding.tvDataResult.text = "暂无数据"
                }
            }
        })
        
        // 观察计数器变化
        viewModel.counter.observe(this, Observer { count ->
            binding.tvCounter.text = count.toString()
        })
        
        // 观察加载状态
        viewModel.loading.observe(this, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnLoadData.isEnabled = !isLoading
        })
        
        // 观察错误消息
        viewModel.errorMessage.observe(this, Observer { message ->
            if (message != null) {
                showError(message)
                // 错误已显示，不需要手动清除
            }
        })
        
        // 观察成功消息
        viewModel.successMessage.observe(this, Observer { message ->
            if (message != null) {
                showSuccess(message)
                // 成功消息已显示，不需要手动清除
            }
        })
        
        // 观察导航事件
        viewModel.navigationEvent.observeEvent(this) { destination ->
            when (destination) {
                "detail" -> {
                    // 这里可以跳转到详情页
                    showToast("导航到详情页功能待实现")
                }
            }
        }
    }
    
    override fun initData() {
        // 首页只做数据加载，欢迎页由 WelcomeActivity 仅展示一次
    }
    
    override fun initListener() {
        // 设置按钮点击事件
        binding.btnLoadData.setOnClickListener {
            WebCommonActivity.start(this, "https://www.baidu.com")
            //viewModel.loadData()
        }
        
        binding.btnIncrement.setOnClickListener {
            viewModel.incrementCounter()
        }
        
        binding.btnDecrement.setOnClickListener {
            viewModel.decrementCounter()
        }
        
        binding.btnReset.setOnClickListener {
            CrashReport.testJavaCrash();
            viewModel.resetCounter()
        }
        
        binding.btnNavigate.setOnClickListener {
            viewModel.navigateToDetail()
        }
    }
    
    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }
    
    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }
}