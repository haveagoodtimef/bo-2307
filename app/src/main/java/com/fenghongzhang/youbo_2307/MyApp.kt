package com.fenghongzhang.youbo_2307

import android.app.Application
import cn.jpush.android.api.JPushInterface
import com.tencent.bugly.crashreport.CrashReport

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        CrashReport.initCrashReport(getApplicationContext(), "5b738a86c1", true);
    }
}