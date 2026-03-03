package com.fenghongzhang.youbo_2307.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API服务接口示例
 * 展示如何使用Retrofit进行网络请求
 */
interface ApiService {
    
    /**
     * 获取用户列表
     */
    @GET("users")
    suspend fun getUsers(): Response<List<UserResponse>>
    
    /**
     * 根据ID获取用户详情
     */
    @GET("users/{id}")
    suspend fun getUserById(@Query("id") userId: Int): Response<UserResponse>
    
    /**
     * 搜索用户
     */
    @GET("users/search")
    suspend fun searchUsers(@Query("query") query: String): Response<List<UserResponse>>
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