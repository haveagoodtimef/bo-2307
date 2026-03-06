package com.fenghongzhang.youbo_2307.ui.haohuo

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.fenghongzhang.youbo_2307.base.BaseFragment
import com.fenghongzhang.youbo_2307.databinding.FragmentHaohuoBinding

/**
 * 好货 Tab 对应 Fragment
 */
class HaohuoFragment : BaseFragment<FragmentHaohuoBinding, HaohuoViewModel>() {

    private val haohuoViewModel: HaohuoViewModel by viewModels()

    override fun getViewBinding(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?) =
        FragmentHaohuoBinding.inflate(inflater, container, false)

    override fun provideViewModel(): HaohuoViewModel? = haohuoViewModel

    override fun initObserver() {
        viewModel.title.observe(viewLifecycleOwner, Observer { title -> binding.tvTitle.text = title })
    }

    override fun initData() {
        viewModel.loadData()
    }

    override fun initListener() {}
}
