# Android MVVM架构示例项目

这是一个完整的Android MVVM架构示例项目，展示了如何使用现代Android开发最佳实践来构建可维护、可测试的应用程序。

## 项目结构

```
app/src/main/java/com/fenghongzhang/youbo_2307/
├── base/                    # 基础架构类
│   ├── BaseActivity.kt     # Activity基类
│   ├── BaseFragment.kt     # Fragment基类
│   ├── BaseRepository.kt   # Repository基类
│   ├── BaseViewModel.kt    # ViewModel基类
│   ├── UiState.kt          # UI状态管理
│   └── Event.kt            # 事件处理
├── viewmodel/              # ViewModel层
│   ├── MainViewModel.kt    # 主页面ViewModel
│   └── UserListViewModel.kt # 用户列表ViewModel
├── repository/             # 数据仓库层
│   ├── MainRepository.kt   # 主页面数据仓库
│   └── UserRepository.kt   # 用户数据仓库
├── network/                # 网络层
│   ├── ApiService.kt       # API接口定义
│   └── NetworkClient.kt    # 网络客户端配置
├── utils/                  # 工具类
│   └── MvvmUtils.kt        # MVVM工具函数
└── MainActivity.kt         # 主页面Activity
```

## 核心组件介绍

### 1. 基础架构类 (Base Classes)

#### BaseActivity<VB, VM>
- 统一的Activity基类，集成ViewBinding和ViewModel
- 自动处理生命周期和资源管理
- 提供常用UI操作方法

#### BaseFragment<VB, VM>
- Fragment基类，支持懒加载和生命周期管理
- 处理Fragment特有的ViewBinding生命周期

#### BaseViewModel
- ViewModel基类，提供通用的状态管理和协程支持
- 内置加载状态、错误处理等功能

#### BaseRepository
- 数据仓库基类，封装安全的协程操作
- 提供网络请求、IO操作的安全执行方法

### 2. 状态管理模式

使用`UiState`密封类管理UI状态：
```kotlin
sealed class UiState<out T> {
    object Initial : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String, val exception: Exception? = null) : UiState<Nothing>()
    object Empty : UiState<Nothing>()
}
```

### 3. 事件处理机制

使用`Event`类处理一次性事件，防止重复消费：
```kotlin
class Event<out T>(private val content: T) {
    var hasBeenHandled = false
    fun getContentIfNotHandled(): T? { /* ... */ }
}
```

## 使用示例

### 创建一个新的MVVM页面

1. **创建ViewModel**
```kotlin
class MyViewModel : BaseViewModel() {
    private val repository = MyRepository()
    
    private val _uiState = MutableLiveData<UiState<MyData>>()
    val uiState: LiveData<UiState<MyData>> = _uiState
    
    fun loadData() {
        launchOnViewModelScope {
            _uiState.value = UiState.Loading
            val result = repository.fetchData()
            
            if (result.isSuccess) {
                _uiState.value = UiState.Success(result.getOrNull()!!)
            } else {
                _uiState.value = UiState.Error(result.exceptionOrNull()?.message ?: "加载失败")
            }
        }
    }
}
```

2. **创建Activity**
```kotlin
class MyActivity : BaseActivity<ActivityMyBinding, MyViewModel>() {
    
    override fun getViewBinding(): ActivityMyBinding {
        return ActivityMyBinding.inflate(layoutInflater)
    }
    
    override fun getViewModelClass(): Class<MyViewModel> {
        return MyViewModel::class.java
    }
    
    override fun initBinding() {
        binding = getViewBinding()
    }
    
    override fun initObserver() {
        viewModel.uiState.observe(this) { uiState ->
            when (uiState) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    hideLoading()
                    updateUI(uiState.data)
                }
                is UiState.Error -> {
                    hideLoading()
                    showError(uiState.message)
                }
                else -> {}
            }
        }
    }
    
    override fun initData() {
        viewModel.loadData()
    }
    
    override fun initListener() {
        binding.button.setOnClickListener {
            viewModel.loadData()
        }
    }
}
```

## 依赖库

项目集成了以下主要依赖：

- **Lifecycle Components**: ViewModel, LiveData
- **Retrofit**: 网络请求
- **OkHttp**: HTTP客户端
- **Gson**: JSON解析
- **Coroutines**: 协程支持
- **Glide**: 图片加载

## 运行项目

1. 确保Android Studio版本 >= Arctic Fox
2. 同步Gradle依赖
3. 连接Android设备或启动模拟器
4. 运行应用

## 功能演示

项目包含以下功能演示：

1. **数据加载**: 展示网络请求和状态管理
2. **计数器**: 展示简单的状态更新
3. **用户列表**: 展示复杂的列表数据处理
4. **搜索功能**: 展示实时数据过滤
5. **缓存机制**: 展示本地数据存储

## 最佳实践

1. **单一职责原则**: 每个组件只负责特定的功能
2. **数据驱动UI**: 通过状态变化驱动界面更新
3. **异步处理**: 使用协程处理耗时操作
4. **错误处理**: 统一的错误处理机制
5. **内存安全**: 正确处理生命周期和资源释放

## 扩展建议

1. 添加单元测试和UI测试
2. 集成Room数据库进行本地存储
3. 添加依赖注入框架(Dagger/Hilt)
4. 实现更复杂的导航和路由
5. 添加数据持久化和离线支持