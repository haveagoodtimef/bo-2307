package com.fenghongzhang.youbo_2307.ui.youbo.featured

import com.fenghongzhang.youbo_2307.base.BaseRepository
import com.fenghongzhang.youbo_2307.db.CarouselDao
import com.fenghongzhang.youbo_2307.db.toEntity
import com.fenghongzhang.youbo_2307.db.toModel
import com.fenghongzhang.youbo_2307.model.CarouselItem
import com.fenghongzhang.youbo_2307.model.LiveFrontCarouselData
import com.fenghongzhang.youbo_2307.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 精选页数据仓库
 * 网络：NetworkModule 提供的 ApiService
 * 本地：Room（DatabaseModule 提供的 CarouselDao），轮播数据写入 DB 并从 DB 读取展示
 */
class FeaturedRepository @Inject constructor(
    private val apiService: ApiService,
    private val carouselDao: CarouselDao
) : BaseRepository() {

    /**
     * 请求轮播接口，成功后写入 Room，并返回结果
     */
    suspend fun getLiveFrontCarousel(): Result<LiveFrontCarouselData> {
        return safeApiCall {
            val res = apiService.getLiveFrontCarousel()
            if (res.isSuccess()) {
                val data = res.data ?: throw Exception(res.message ?: "data 为空")
                saveCarouselItems(data.carouselList.ifEmpty { data.topPorcelainList })
                data
            } else {
                throw Exception(res.message ?: "请求失败 code=${res.code}")
            }
        }
    }

    /**
     * 将轮播列表写入数据库（先清空再插入）
     */
    suspend fun saveCarouselItems(items: List<CarouselItem>) {
        safeIoOperation {
            carouselDao.deleteAll()
            if (items.isNotEmpty()) {
                carouselDao.insertAll(items.map { it.toEntity() })
            }
        }
    }

    /**
     * 从数据库读取轮播列表（Flow，可观察变化）
     */
    fun getCarouselFromDb(): Flow<List<CarouselItem>> {
        return carouselDao.getAllFlow().map { list -> list.map { it.toModel() } }
    }

    /**
     * 一次性从数据库读取轮播列表（用于远程无数据/失败时回退到本地）
     */
    suspend fun getCarouselListOnce(): List<CarouselItem> {
        return safeIoOperation<List<CarouselItem>> { carouselDao.getAll().map { it.toModel() } }
            .getOrElse { emptyList() }
    }

    /**
     * 若数据库中无数据，则插入两条示例轮播数据
     */
    suspend fun ensureTwoCarouselItemsInDb() {
        safeIoOperation {
            if (carouselDao.count() == 0) {
                val sample = listOf(
                    CarouselItem(
                        ad_code = "http://img.ubo.vip/data/appBanner/1742017986620356707.jpg",
                        ad_link = "https://h5.youboi.com/",
                        ad_name = "示例轮播1",
                        is_belong = "1",
                        ad_id = "sample_1",
                        start_time = "${System.currentTimeMillis() / 1000}",
                        banner_color = null,
                        extra = "0"
                    ),
                    CarouselItem(
                        ad_code = "http://img.ubo.vip/data/appBanner/1742017986620356707.jpg",
                        ad_link = "https://h5.youboi.com/",
                        ad_name = "示例轮播2",
                        is_belong = "2",
                        ad_id = "sample_2",
                        start_time = "${System.currentTimeMillis() / 1000}",
                        banner_color = null,
                        extra = "0"
                    )
                )
                carouselDao.insertAll(sample.map { it.toEntity() })
            }
        }
    }
}
