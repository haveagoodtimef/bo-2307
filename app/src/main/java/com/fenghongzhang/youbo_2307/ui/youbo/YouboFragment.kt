package com.fenghongzhang.youbo_2307.ui.youbo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import com.fenghongzhang.youbo_2307.R
import com.fenghongzhang.youbo_2307.base.BaseFragment
import com.fenghongzhang.youbo_2307.databinding.FragmentYouboBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 有播 Tab 对应 Fragment：顶部 关注/精选/附近 用 ViewPager2 展示三个 Fragment，带搜索栏
 */
@AndroidEntryPoint
class YouboFragment : BaseFragment<FragmentYouboBinding, YouboViewModel>() {

    private val youboViewModel: YouboViewModel by viewModels()

    override fun getViewBinding(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?) =
        FragmentYouboBinding.inflate(inflater, container, false)

    override fun provideViewModel(): YouboViewModel? = youboViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPagerAndTabs()
    }

    override fun initObserver() {
        viewModel.title.observe(viewLifecycleOwner, Observer { })
    }

    override fun initData() {
        viewModel.loadData()
    }

    /** 在 onViewCreated 中直接调用，保证 ViewPager2 与 TabLayout 一定会被设置（不依赖 BaseFragment 懒加载） */
    private fun setupViewPagerAndTabs() {
        binding.viewPager2.adapter = YouboPagerAdapter(this)
        val tabTitles = listOf(
            getString(R.string.youhuo_tab_follow),
            getString(R.string.youhuo_tab_featured),
            getString(R.string.youhuo_tab_nearby)
        )
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    override fun initListener() {
        binding.btnSearch.setOnClickListener { showToast("搜索") }
        binding.ivCart.setOnClickListener { showToast("购物车") }
        binding.ivHeaderLeft.setOnClickListener { showToast("日程") }
        binding.ivHeaderRight.setOnClickListener { showToast("扫一扫") }
    }
}
