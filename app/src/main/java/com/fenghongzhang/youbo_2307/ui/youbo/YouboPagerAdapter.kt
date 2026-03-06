package com.fenghongzhang.youbo_2307.ui.youbo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fenghongzhang.youbo_2307.ui.youbo.featured.FeaturedFragment
import com.fenghongzhang.youbo_2307.ui.youbo.follow.FollowFragment
import com.fenghongzhang.youbo_2307.ui.youbo.nearby.NearbyFragment

/**
 * 有播页 ViewPager2 适配器：关注 / 精选 / 附近 三个 Fragment
 */
class YouboPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> FollowFragment()
        1 -> FeaturedFragment()
        2 -> NearbyFragment()
        else -> FollowFragment()
    }
}
