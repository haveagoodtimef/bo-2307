package com.fenghongzhang.youbo_2307.utils


import android.app.Application
import android.content.Context
import android.os.Build
import android.webkit.WebView



/**
 * @author 田桂森 2020/8/14 0014
 */
object LibApp {
    /**
     * Logger日志的tag
     */
    const val LOGGER_TAG = "logtag"
    lateinit var app: Application

    /**
     * APP标记，用于标记不同的app
     * 0是旧有播
     * 1是新有播
     * 2是有宝
     */
    @JvmField
    var appSign = 0

    @JvmStatic
    fun init(appContext: Application): LibApp {
        appContext.apply {
            app = this
        }
        return this
    }

    /**
     * 设置默认标题风格
     */


}