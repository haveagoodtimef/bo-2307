package com.fenghongzhang.youbo_2307.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fenghongzhang.youbo_2307.base.BaseViewModel
import com.fenghongzhang.youbo_2307.base.UiState
import com.fenghongzhang.youbo_2307.network.UserResponse
import com.fenghongzhang.youbo_2307.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 用户列表 ViewModel
 * UserRepository（内部使用 ApiService）由 Hilt 注入。
 */
@HiltViewModel
class UserListViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel() {
    
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

    }
}