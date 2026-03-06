package com.fenghongzhang.youbo_2307.ui.youbo.featured

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fenghongzhang.youbo_2307.base.BaseFragment
import com.fenghongzhang.youbo_2307.base.UiState
import com.fenghongzhang.youbo_2307.databinding.FragmentYouboFeaturedBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 精选页 Fragment（ViewPager2 第 2 页）
 * RecyclerView 多布局展示，当前仅头部 Banner（getLiveFrontCarousel）
 */
@AndroidEntryPoint
class FeaturedFragment : BaseFragment<FragmentYouboFeaturedBinding, FeaturedViewModel>() {

    private val featuredViewModel: FeaturedViewModel by viewModels()

    private val featuredAdapter = FeaturedAdapter()

    override fun getViewBinding(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?) =
        FragmentYouboFeaturedBinding.inflate(inflater, container, false)

    override fun provideViewModel(): FeaturedViewModel? = featuredViewModel

    override fun initObserver() {
        viewModel.uiState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is UiState.Success -> featuredAdapter.list = state.data
                is UiState.Loading -> { /* 可显示 loading */ }
                is UiState.Error -> showError(state.message)
                else -> { }
            }
        })
    }

    override fun initData() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = featuredAdapter
        viewModel.loadData()
    }

    override fun initListener() {}
}
