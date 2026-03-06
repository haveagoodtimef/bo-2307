package com.fenghongzhang.youbo_2307.ui.youbo.featured

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fenghongzhang.youbo_2307.base.BaseViewModel
import com.fenghongzhang.youbo_2307.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 精选页 ViewModel
 * 调用 FeaturedRepository.getLiveFrontCarousel，结果通过 uiState 暴露列表（多布局，当前仅 Banner）
 */
@HiltViewModel
class FeaturedViewModel @Inject constructor(
    private val repository: FeaturedRepository
) : BaseViewModel() {

    private val _uiState = MutableLiveData<UiState<List<FeaturedListItem>>>(UiState.Initial)
    val uiState: LiveData<UiState<List<FeaturedListItem>>> = _uiState

    fun loadData() {
        launchOnViewModelScope {
            showLoading()
            _uiState.value = UiState.Loading

            val result = repository.getLiveFrontCarousel()

            if (result.isSuccess) {
                val data = result.getOrNull()!!
                val list = mutableListOf<FeaturedListItem>()
                val carousel = data.carouselList.ifEmpty { data.topPorcelainList }
                if (carousel.isNotEmpty()) {
                    list.add(FeaturedListItem.Banner(carousel))
                }
                _uiState.value = UiState.Success(list)
                hideLoading()
            } else {
                val msg = result.exceptionOrNull()?.message ?: "加载失败"
                _uiState.value = UiState.Error(msg, result.exceptionOrNull() as? Exception)
                showError(msg)
            }
        }
    }
}
