package com.fenghongzhang.youbo_2307.repository

import com.fenghongzhang.youbo_2307.base.BaseRepository
import com.fenghongzhang.youbo_2307.network.NetworkClient
import com.fenghongzhang.youbo_2307.network.UserResponse

/**
 * 用户数据仓库
 * 展示真实网络请求与本地数据结合使用
 */
class UserRepository : BaseRepository() {
    
    /**
     * 获取用户列表
     */
    suspend fun getUsers(): Result<List<UserResponse>> {
        return safeApiCall {
            val response = NetworkClient.apiService.getUsers()
            if (response.isSuccessful) {
                response.body() ?: throw Exception("响应体为空")
            } else {
                throw Exception("请求失败: ${response.code()} ${response.message()}")
            }
        }
    }
    
    /**
     * 根据ID获取用户详情
     */
    suspend fun getUserById(userId: Int): Result<UserResponse> {
        return safeApiCall {
            val response = NetworkClient.apiService.getUserById(userId)
            if (response.isSuccessful) {
                response.body() ?: throw Exception("用户不存在")
            } else {
                throw Exception("请求失败: ${response.code()} ${response.message()}")
            }
        }
    }
    
    /**
     * 搜索用户
     */
    suspend fun searchUsers(query: String): Result<List<UserResponse>> {
        return safeApiCall {
            // 这里模拟搜索功能，实际项目中会有对应的API
            val allUsers = getUsers().getOrThrow()
            allUsers.filter { 
                it.name.contains(query, ignoreCase = true) || 
                it.username.contains(query, ignoreCase = true) ||
                it.email.contains(query, ignoreCase = true)
            }
        }
    }
    
    /**
     * 缓存用户数据到本地（模拟）
     */
    private val localUserCache = mutableMapOf<Int, UserResponse>()
    
    suspend fun getCachedUser(userId: Int): Result<UserResponse?> {
        return safeIoOperation {
            localUserCache[userId]
        }
    }
    
    suspend fun cacheUser(user: UserResponse): Result<Unit> {
        return safeIoOperation {
            localUserCache[user.id] = user
        }
    }
    
    /**
     * 获取本地缓存的所有用户
     */
    suspend fun getAllCachedUsers(): Result<List<UserResponse>> {
        return safeIoOperation {
            localUserCache.values.toList()
        }
    }
}