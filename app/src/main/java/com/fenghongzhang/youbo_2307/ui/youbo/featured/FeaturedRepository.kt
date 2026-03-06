package com.fenghongzhang.youbo_2307.ui.youbo.featured

import com.fenghongzhang.youbo_2307.base.BaseRepository
import com.fenghongzhang.youbo_2307.model.LiveFrontCarouselData
import com.fenghongzhang.youbo_2307.network.ApiService
import javax.inject.Inject

/**
 * 精选页数据仓库
 * 通过 NetworkModule 提供的 ApiService 请求 getLiveFrontCarousel
 */
class FeaturedRepository @Inject constructor(
    private val apiService: ApiService
) : BaseRepository() {

    suspend fun getLiveFrontCarousel(): Result<LiveFrontCarouselData> {
        return safeApiCall {
            val res = apiService.getLiveFrontCarousel()
            if (res.isSuccess()) {
                res.data ?: throw Exception(res.message ?: "data 为空")
            } else {
                throw Exception(res.message ?: "请求失败 code=${res.code}")
            }
        }
    }
}
