package com.fenghongzhang.youbo_2307.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 轮播图 Room 实体，对应表 carousel_item
 */
@Entity(tableName = "carousel_item")
data class CarouselItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val ad_code: String,
    val ad_link: String,
    val ad_name: String,
    val is_belong: String,
    val ad_id: String,
    val start_time: String,
    val banner_color: String? = null,
    val extra: String? = null
)
