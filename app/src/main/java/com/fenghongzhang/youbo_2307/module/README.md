# Hilt 依赖模块

## NetworkModule

通过 Hilt 提供网络相关单例：

- **OkHttpClient**：连接/读写超时、日志拦截器
- **Retrofit**：基于上述 OkHttpClient，Gson 转换，BASE_URL = `https://jsonplaceholder.typicode.com/`
- **ApiService**：Retrofit 创建的接口实现

## ApiService 使用方式

1. **在 Repository 中注入**（推荐）

```kotlin
class UserRepository @Inject constructor(
    private val apiService: ApiService
) : BaseRepository() {
    suspend fun getUsers(): Result<List<UserResponse>> = safeApiCall {
        val r = apiService.getUsers()
        if (r.isSuccessful) Result.success(r.body() ?: emptyList())
        else Result.failure(Exception("${r.code()} ${r.message()}"))
    }
}
```

2. **在 ViewModel 中通过 Repository 使用**

```kotlin
@HiltViewModel
class UserListViewModel @Inject constructor(
    private val repository: UserRepository  // 内部已使用 ApiService
) : BaseViewModel() { ... }
```

3. **在 Activity/Fragment 中直接注入**（不推荐，建议走 Repository）

```kotlin
@AndroidEntryPoint
class SomeActivity : AppCompatActivity() {
    @Inject lateinit var apiService: ApiService
}
```

依赖链：`NetworkModule` → `Retrofit` / `ApiService` → `UserRepository` → `UserListViewModel`。
