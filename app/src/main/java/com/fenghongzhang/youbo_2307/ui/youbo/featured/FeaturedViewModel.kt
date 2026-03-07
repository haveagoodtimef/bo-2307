package com.fenghongzhang.youbo_2307.ui.youbo.featured

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fenghongzhang.youbo_2307.base.BaseViewModel
import com.fenghongzhang.youbo_2307.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * 精选页 ViewModel
 * 从 Room 读取轮播数据并展示；loadData 时先确保 DB 有两条示例数据，再请求接口并写库
 */
@HiltViewModel
class FeaturedViewModel @Inject constructor(
    private val repository: FeaturedRepository
) : BaseViewModel() {

    private val _uiState = MutableLiveData<UiState<List<FeaturedListItem>>>(UiState.Initial)
    val uiState: LiveData<UiState<List<FeaturedListItem>>> = _uiState

    init {
        repository.getCarouselFromDb()
            .onEach { list ->
                val items = if (list.isEmpty()) emptyList() else listOf(FeaturedListItem.Banner(list))
                _uiState.value = UiState.Success(items)
            }
            .catch { e -> _uiState.value = UiState.Error(e.message ?: "加载失败", e as? Exception) }
            .launchIn(viewModelScope)
    }

    fun loadData() {
        launchOnViewModelScope {
            showLoading()
            _uiState.value = UiState.Loading

            repository.ensureTwoCarouselItemsInDb()

            val result = repository.getLiveFrontCarousel()

            if (result.isSuccess) {
                val data = result.getOrNull()!!
                val remoteList = data.carouselList.ifEmpty { data.topPorcelainList }
                if (remoteList.isEmpty()) {
                    val localList = repository.getCarouselListOnce()
                    val items = if (localList.isEmpty()) emptyList() else listOf(FeaturedListItem.Banner(localList))
                    _uiState.value = UiState.Success(items)
                }
                hideLoading()
            } else {
                val localList = repository.getCarouselListOnce()
                if (localList.isNotEmpty()) {
                    _uiState.value = UiState.Success(listOf(FeaturedListItem.Banner(localList)))
                } else {
                    val msg = result.exceptionOrNull()?.message ?: "加载失败"
                    _uiState.value = UiState.Error(msg, result.exceptionOrNull() as? Exception)
                }
                hideLoading()
            }
        }
    }
}
