package com.fenghongzhang.youbo_2307.ui.youbo.nearby

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.fenghongzhang.youbo_2307.base.BaseFragment
import com.fenghongzhang.youbo_2307.base.UiState
import com.fenghongzhang.youbo_2307.databinding.FragmentYouboNearbyBinding

/**
 * 附近页 Fragment（ViewPager2 第 3 页）
 */
class NearbyFragment : BaseFragment<FragmentYouboNearbyBinding, NearbyViewModel>() {

    private val nearbyViewModel: NearbyViewModel by viewModels()

    override fun getViewBinding(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?) =
        FragmentYouboNearbyBinding.inflate(inflater, container, false)

    override fun provideViewModel(): NearbyViewModel? = nearbyViewModel

    override fun initObserver() {
        viewModel.uiState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is UiState.Success -> binding.tvTitle.text = state.data
                is UiState.Loading -> binding.tvTitle.text = "加载中..."
                is UiState.Error -> binding.tvTitle.text = state.message
                else -> binding.tvTitle.text = "附近"
            }
        })
    }

    override fun initData() {
        viewModel.loadData()
    }

    override fun initListener() {}
}
