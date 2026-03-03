package com.fenghongzhang.youbo_2307.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * MVVM架构中Repository的基类
 * 负责数据处理和业务逻辑
 */
abstract class BaseRepository {
    
    /**
     * 安全执行网络请求
     * @param call 网络请求函数
     * @return Result包装的结果
     */
    protected suspend fun <T> safeApiCall(call: suspend () -> T): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                Result.success(call())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    /**
     * 执行耗时操作
     * @param block 耗时操作代码块
     * @return Result包装的结果
     */
    protected suspend fun <T> safeIoOperation(block: suspend () -> T): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                Result.success(block())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    /**
     * 在主线程执行操作
     * @param block 主线程操作代码块
     * @return Result包装的结果
     */
    protected suspend fun <T> safeMainOperation(block: suspend () -> T): Result<T> {
        return withContext(Dispatchers.Main) {
            try {
                Result.success(block())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}