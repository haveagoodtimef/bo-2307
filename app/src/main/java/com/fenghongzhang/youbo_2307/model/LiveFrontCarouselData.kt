package com.fenghongzhang.youbo_2307.model

// ---------- 直播前台轮播接口 data 字段 ----------

/**
 * 直播前台轮播接口 /index.php?r=lv/live-front/carousel 的 data 结构
 */

data class LiveFrontCarouselData(
    val topPorcelainList: List<CarouselItem> = emptyList(),
    val carouselList: List<CarouselItem> = emptyList()
)