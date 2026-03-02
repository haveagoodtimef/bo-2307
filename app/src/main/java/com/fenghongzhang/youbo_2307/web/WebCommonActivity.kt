package com.fenghongzhang.youbo_2307.web

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fenghongzhang.youbo_2307.R
import retrofit2.http.Url

class WebCommonActivity : AppCompatActivity() {
    lateinit var  webView:WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_web_common)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. 获取WebView实例（修正Java中遗漏的括号和id）
       webView = findViewById(R.id.webView) // 替换成你实际的WebView id

        // 2. 获取WebSettings并开启JavaScript（Kotlin中属性调用替代getter方法）
        val settings: WebSettings = webView.settings // 等价于webView.getSettings()
        settings.javaScriptEnabled = true // 开启JS
        settings.domStorageEnabled = true // 开启DOM存储
        settings.allowFileAccess = true // 允许访问本地文件
        settings.javaScriptCanOpenWindowsAutomatically = true // 允许JS打开窗口


        // 配置WebView弹窗回调（解决alert/confirm阻塞问题）
        webView.webChromeClient = object: WebChromeClient() {

            override fun onConsoleMessage(message: String, lineNumber: Int, sourceID: String) {
                Log.d("JSConsole", "JS错误：$message 行号：$lineNumber")
                super.onConsoleMessage(message, lineNumber, sourceID)
            }

            // 处理alert弹窗（可选，避免阻塞）
            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                Toast.makeText(this@WebCommonActivity, message, Toast.LENGTH_LONG).show()
                return true
            }
        }

// 等待页面加载完成后再调用JS方法（关键：避免页面未加载完调用返回null）
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d("WebView", "页面加载完成，URL：$url")
                // 页面加载完成后立即调用JS方法
                callJS()
            }

            // 监听页面加载错误（关键：排查文件是否找不到）
            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                Log.e("WebViewError", "加载失败：$description，URL：$failingUrl")
            }
        }

        webView.addJavascriptInterface(AndroidJSInterface(), "AndroidInterface")

        // 3. 加载本地assets目录下的HTML文件
        webView.loadUrl("file:///android_asset/javascript.html")

        // 方式1：使用loadUrl调用JS方法（修正Java中遗漏的括号和方法参数）


        // 方式2：使用evaluateJavascript调用JS方法（Kotlin用Lambda简化ValueCallback）
//        webView.evaluateJavascript("callJS()") { value ->
//            // 注意：返回值会被包裹双引号，需要去除
//            val realValue = value?.replace("\"", "") // 去除双引号，得到 Hello from JS
//            Log.d("WebView", "返回值：$value | 处理后：$realValue")
//            // 此时输出：返回值："Hello from JS" | 处理后：Hello from JS
//        }


    }

    inner class AndroidJSInterface {
        // 方法1：带参数，有返回值
        @JavascriptInterface
        fun showToast(msg: String): String {
            // 运行在子线程，需切到主线程更新UI（Toast/Log等）
            runOnUiThread {
                Toast.makeText(this@WebCommonActivity, "JS调用了Android的showToast：$msg", Toast.LENGTH_LONG).show()
            }
            Log.d("AndroidJS", "JS传递的参数：$msg")
            return "Toast已显示，参数：$msg" // 返回值给JS
        }

        // 方法2：无参数，有返回值
        @JavascriptInterface
        fun getAndroidInfo(): String {
            val info = "设备品牌：${android.os.Build.BRAND}，系统版本：${android.os.Build.VERSION.RELEASE}"
            Log.d("AndroidJS", "JS调用了getAndroidInfo，返回：$info")
            return info // 返回设备信息给JS
        }
    }

    // 封装调用JS的方法
    private fun callJS() {
        // 方式1：先打印JS上下文，确认是否能访问到函数
        webView.loadUrl("javascript:callJS()") // 注意：JS方法调用要加()
//        webView.evaluateJavascript("typeof callJS") { type ->
//            Log.d("WebView", "callJS函数类型：$type") // 正常应该返回 "function"，返回 "undefined" 表示函数不存在
//
//            // 方式2：调用callJS函数
//            webView.evaluateJavascript("callJS()") { value ->
//                Log.d("WebView", "返回值：$value | 处理后：${value?.replace("\"", "")}")
//            }
//        }
    }
    //伴生对象 -》 静态成员
    companion object {
        @JvmStatic
        fun start(act: Activity, url: String) {
            act.startActivity(Intent(act, WebCommonActivity::class.java))
        }
    }
}