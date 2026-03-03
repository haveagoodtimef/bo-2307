package com.fenghongzhang.youbo_2307.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fenghongzhang.youbo_2307.base.BaseViewModel
import com.fenghongzhang.youbo_2307.base.UiState
import com.fenghongzhang.youbo_2307.network.UserResponse
import com.fenghongzhang.youbo_2307.repository.UserRepository

/**
 * 用户列表ViewModel
 * 展示复杂的数据处理和状态管理
 */
class UserListViewModel : BaseViewModel() {
    
    private val repository = UserRepository()
    
    // 用户列表状态
    private val _userListState = MutableLiveData<UiState<List<UserResponse>>>()
    val userListState: LiveData<UiState<List<UserResponse>>> = _userListState
    
    // 搜索结果状态
    private val _searchState = MutableLiveData<UiState<List<UserResponse>>>()
    val searchState: LiveData<UiState<List<UserResponse>>> = _searchState
    
    // 选中的用户
    private val _selectedUser = MutableLiveData<UserResponse?>()
    val selectedUser: LiveData<UserResponse?> = _selectedUser
    
    /**
     * 加载用户列表
     */
    fun loadUsers(refresh: Boolean = false) {
        launchOnViewModelScope {
            showLoading()
            
            // 如果不是刷新，先检查缓存
            if (!refresh) {
                val cachedUsers = repository.getAllCachedUsers()
                if (cachedUsers.isSuccess && !(cachedUsers.getOrNull()?.isEmpty() ?: true)) {
                    _userListState.value = UiState.Success(cachedUsers.getOrNull()!!)
                    hideLoading()
                    return@launchOnViewModelScope
                }
            }
            
            _userListState.value = UiState.Loading
            
            val result = repository.getUsers()
            
            if (result.isSuccess) {
                val users = result.getOrNull() ?: emptyList()
                
                // 缓存数据
                users.forEach { user ->
                    repository.cacheUser(user)
                }
                
                _userListState.value = UiState.Success(users)
                showSuccess("加载了 ${users.size} 个用户")
            } else {
                _userListState.value = UiState.Error(
                    result.exceptionOrNull()?.message ?: "加载用户列表失败",
                    result.exceptionOrNull() as Exception?
                )
                showError(result.exceptionOrNull()?.message ?: "加载用户列表失败")
            }
            
            hideLoading()
        }
    }
    
    /**
     * 搜索用户
     */
    fun searchUsers(query: String) {
        if (query.isBlank()) {
            _searchState.value = UiState.Empty
            return
        }
        
        launchOnViewModelScope {
            _searchState.value = UiState.Loading
            
            val result = repository.searchUsers(query.trim())
            
            if (result.isSuccess) {
                val users = result.getOrNull() ?: emptyList()
                if (users.isEmpty()) {
                    _searchState.value = UiState.Empty
                } else {
                    _searchState.value = UiState.Success(users)
                }
            } else {
                _searchState.value = UiState.Error(
                    result.exceptionOrNull()?.message ?: "搜索失败",
                    result.exceptionOrNull() as Exception?
                )
            }
        }
    }
    
    /**
     * 选择用户
     */
    fun selectUser(user: UserResponse) {
        _selectedUser.value = user
    }
    
    /**
     * 清除选中用户
     */
    fun clearSelectedUser() {
        _selectedUser.value = null
    }
    
    /**
     * 刷新用户列表
     */
    fun refreshUsers() {
        loadUsers(refresh = true)
    }
}