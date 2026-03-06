package com.fenghongzhang.youbo_2307.ui.youbo.featured

import com.fenghongzhang.youbo_2307.model.CarouselItem

/**
 * 精选页多布局列表项
 */
sealed class FeaturedListItem {
    /** 头部轮播 Banner */
    data class Banner(val items: List<CarouselItem>) : FeaturedListItem()
}
