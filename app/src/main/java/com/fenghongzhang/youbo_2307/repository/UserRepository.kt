package com.fenghongzhang.youbo_2307.repository

import com.fenghongzhang.youbo_2307.base.BaseRepository
import com.fenghongzhang.youbo_2307.network.ApiService
import com.fenghongzhang.youbo_2307.network.UserResponse
import javax.inject.Inject

/**
 * 用户数据仓库
 * ApiService 由 Hilt [com.fenghongzhang.youbo_2307.module.NetworkModule] 提供，此处通过构造函数注入使用。
 */
class UserRepository @Inject constructor(
    private val apiService: ApiService
) : BaseRepository() {




    
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