package com.fenghongzhang.youbo_2307.ui.youbo.follow

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.fenghongzhang.youbo_2307.base.BaseFragment
import com.fenghongzhang.youbo_2307.base.UiState
import com.fenghongzhang.youbo_2307.databinding.FragmentYouboFollowBinding

/**
 * 关注页 Fragment（ViewPager2 第 1 页）
 */
class FollowFragment : BaseFragment<FragmentYouboFollowBinding, FollowViewModel>() {

    private val followViewModel: FollowViewModel by viewModels()

    override fun getViewBinding(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?) =
        FragmentYouboFollowBinding.inflate(inflater, container, false)

    override fun provideViewModel(): FollowViewModel? = followViewModel

    override fun initObserver() {
        viewModel.uiState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is UiState.Success -> binding.tvTitle.text = state.data
                is UiState.Loading -> binding.tvTitle.text = "加载中..."
                is UiState.Error -> binding.tvTitle.text = state.message
                else -> binding.tvTitle.text = "关注"
            }
        })
    }

    override fun initData() {
        viewModel.loadData()
    }

    override fun initListener() {}
}
