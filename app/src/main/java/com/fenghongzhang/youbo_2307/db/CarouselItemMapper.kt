package com.fenghongzhang.youbo_2307.db

import com.fenghongzhang.youbo_2307.model.CarouselItem

/**
 * Entity 与 Model 互转
 */
fun CarouselItemEntity.toModel(): CarouselItem = CarouselItem(
    ad_code = ad_code,
    ad_link = ad_link,
    ad_name = ad_name,
    is_belong = is_belong,
    ad_id = ad_id,
    start_time = start_time,
    banner_color = banner_color,
    extra = extra
)

fun CarouselItem.toEntity(): CarouselItemEntity = CarouselItemEntity(
    ad_code = ad_code,
    ad_link = ad_link,
    ad_name = ad_name,
    is_belong = is_belong,
    ad_id = ad_id,
    start_time = start_time,
    banner_color = banner_color,
    extra = extra
)
