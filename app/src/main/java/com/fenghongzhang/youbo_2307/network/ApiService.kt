package com.fenghongzhang.youbo_2307.network

import com.fenghongzhang.youbo_2307.model.LiveFrontCarouselData
import com.fenghongzhang.youbo_2307.retrofit.ResWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API 服务接口
 * 由 Hilt [com.fenghongzhang.youbo_2307.module.NetworkModule] 提供实例，
 * 在 Repository/ViewModel 中通过构造函数注入使用，例如：
 * ```
 * class UserRepository @Inject constructor(private val apiService: ApiService) : BaseRepository() {
 *     suspend fun getUsers(): Result<List<UserResponse>> = safeApiCall {
 *         val r = apiService.getUsers()
 *         if (r.isSuccessful) Result.success(r.body() ?: emptyList())
 *         else Result.failure(Exception("${r.code()} ${r.message()}"))
 *     }
 * }
 * ```
 */
interface ApiService {

    /**
     * 根据 ID 获取用户详情
     */
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") userId: Int): ResWrapper<UserResponse>

    /**
     * 直播前台轮播图
     * 返回 topPorcelainList、carouselList
     */
    @GET("index.php")
    suspend fun getLiveFrontCarousel(@Query("r") r: String = "lv/live-front/carousel----1"): ResWrapper<LiveFrontCarouselData>
}

/**
 * 用户响应数据类
 */
data class UserResponse(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String,
    val address: AddressResponse,
    val company: CompanyResponse
)

data class AddressResponse(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: GeoResponse
)

data class GeoResponse(
    val lat: String,
    val lng: String
)

data class CompanyResponse(
    val name: String,
    val catchPhrase: String,
    val bs: String
)



