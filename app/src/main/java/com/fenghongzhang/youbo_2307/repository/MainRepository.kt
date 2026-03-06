package com.fenghongzhang.youbo_2307.repository

import com.fenghongzhang.youbo_2307.base.BaseRepository
import com.fenghongzhang.youbo_2307.datasource.main.MainLocalDataSource
import com.fenghongzhang.youbo_2307.datasource.main.MainRemoteDataSource
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * 主页面数据仓库示例
 * 展示如何使用BaseRepository进行数据处理
 */

class MainRepository @Inject constructor
    (val remoteDataSource: MainRemoteDataSource,
     val localDataSource: MainLocalDataSource) : BaseRepository() {

    /**
     * 模拟从网络或本地获取数据
     * 实际项目中这里会调用API接口或数据库查询
     */
    suspend fun fetchData(): Result<String> {

        return safeApiCall {
            // 模拟网络延迟
            delay(2000)

            // 模拟可能的网络错误（10%概率失败）
            if (Math.random() < 0.1) {
                throw Exception("网络连接异常，请稍后重试")
            }

            "这是从服务器获取的数据 - ${System.currentTimeMillis()}"
        }
    }
    
    /**
     * 模拟保存数据到服务器
     */
    suspend fun saveData(data: String): Result<Boolean> {
        return safeApiCall {
            // 模拟网络延迟
            delay(1500)
            
            // 模拟保存操作
            println("保存数据: $data")
            true
        }
    }
    
    /**
     * 模拟获取用户信息
     */
    suspend fun getUserInfo(): Result<UserInfo> {
        return safeApiCall {
            delay(1000)
            
            UserInfo(
                id = 1,
                name = "张三",
                email = "zhangsan@example.com",
                avatar = "https://example.com/avatar.jpg"
            )
        }
    }
}

/**
 * 用户信息数据类
 */
data class UserInfo(
    val id: Int,
    val name: String,
    val email: String,
    val avatar: String
)