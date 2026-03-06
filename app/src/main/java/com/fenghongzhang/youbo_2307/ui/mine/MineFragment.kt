package com.fenghongzhang.youbo_2307.ui.mine

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.fenghongzhang.youbo_2307.base.BaseFragment
import com.fenghongzhang.youbo_2307.databinding.FragmentMineBinding

/**
 * 我的 Tab 对应 Fragment
 */
class MineFragment : BaseFragment<FragmentMineBinding, MineViewModel>() {

    private val mineViewModel: MineViewModel by viewModels()

    override fun getViewBinding(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?) =
        FragmentMineBinding.inflate(inflater, container, false)

    override fun provideViewModel(): MineViewModel? = mineViewModel

    override fun initObserver() {
        viewModel.title.observe(viewLifecycleOwner, Observer { title -> binding.tvTitle.text = title })
    }

    override fun initData() {
        viewModel.loadData()
    }

    override fun initListener() {}
}
