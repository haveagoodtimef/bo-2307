package com.fenghongzhang.youbo_2307

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.fenghongzhang.youbo_2307.base.BaseActivity
import com.fenghongzhang.youbo_2307.databinding.ActivityMainBinding
import com.fenghongzhang.youbo_2307.ui.haohuo.HaohuoFragment
import com.fenghongzhang.youbo_2307.ui.message.MessageFragment
import com.fenghongzhang.youbo_2307.ui.mine.MineFragment
import com.fenghongzhang.youbo_2307.ui.youbo.YouboFragment
import com.fenghongzhang.youbo_2307.navigation.LottieTabView
import com.fenghongzhang.youbo_2307.test.UserFeng
import com.fenghongzhang.youbo_2307.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import javax.inject.Inject

/**
 * 首页 - 底部四个 Tab：有货、好货、消息、我的，分别对应四个 Fragment
 */
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private val mainViewModel: MainViewModel by viewModels()

    private val youboFragment = YouboFragment()
    private val haohuoFragment = HaohuoFragment()
    private val messageFragment = MessageFragment()
    private val mineFragment = MineFragment()


    @Inject
    lateinit var userFeng: UserFeng

    private var currentFragment: Fragment? = null
    private var currentTab: LottieTabView? = null


    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initBinding() {
        binding = getViewBinding()
        viewModel = mainViewModel
    }

    override fun initObserver() {}

    override fun initData() {
        userFeng.name = "FengHongZhang"
        println("userFeng: $userFeng")
        // 默认展示「有货」Fragment
        switchFragment(youboFragment, binding.tabLive)
    }

    override fun initListener() {
        binding.tabLive.setOnClickListener { switchFragment(youboFragment, binding.tabLive) }
        binding.tabYouxuan.setOnClickListener { switchFragment(haohuoFragment, binding.tabYouxuan) }
        binding.tabMsg.setOnClickListener { switchFragment(messageFragment, binding.tabMsg) }
        binding.tabMine.setOnClickListener { switchFragment(mineFragment, binding.tabMine) }
    }

    private fun switchFragment(target: Fragment, tab: LottieTabView) {
        if (currentFragment == target) return
        currentTab?.unSelected()
        tab.selected()
        currentTab = tab
        supportFragmentManager.beginTransaction().apply {
            currentFragment?.let { hide(it) }
            if (target.isAdded) {
                show(target)
            } else {
                add(binding.fl.id, target)
            }
            commit()
        }
        currentFragment = target
    }
}
