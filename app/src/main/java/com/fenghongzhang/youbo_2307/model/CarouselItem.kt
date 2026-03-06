package com.fenghongzhang.youbo_2307.model

/**
 * 轮播项
 */
data class CarouselItem(
    val ad_code: String,
    val ad_link: String,
    val ad_name: String,
    val is_belong: String,
    val ad_id: String,
    val start_time: String,
    val banner_color: String? = null,
    val extra: String? = null
)