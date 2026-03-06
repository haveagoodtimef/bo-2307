package com.fenghongzhang.youbo_2307.ui.message

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.fenghongzhang.youbo_2307.base.BaseFragment
import com.fenghongzhang.youbo_2307.databinding.FragmentMessageBinding

/**
 * 消息 Tab 对应 Fragment
 */
class MessageFragment : BaseFragment<FragmentMessageBinding, MessageViewModel>() {

    private val messageViewModel: MessageViewModel by viewModels()

    override fun getViewBinding(inflater: android.view.LayoutInflater, container: android.view.ViewGroup?) =
        FragmentMessageBinding.inflate(inflater, container, false)

    override fun provideViewModel(): MessageViewModel? = messageViewModel

    override fun initObserver() {
        viewModel.title.observe(viewLifecycleOwner, Observer { title -> binding.tvTitle.text = title })
    }

    override fun initData() {
        viewModel.loadData()
    }

    override fun initListener() {}
}
