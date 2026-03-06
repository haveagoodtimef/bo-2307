package com.fenghongzhang.youbo_2307

import android.app.Application
import cn.jpush.android.api.JPushInterface
import com.fenghongzhang.youbo_2307.utils.LibApp
import com.tencent.bugly.crashreport.CrashReport
import dagger.hilt.android.HiltAndroidApp
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.unit.Subunits

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        LibApp.init(this)
        AutoSizeConfig.getInstance()
            .setCustomFragment(true)
//                .setBaseOnWidth(false)//以高度适配
            .setUseDeviceSize(true)
            .unitsManager
            .setSupportDP(false)
            .setSupportSP(false)
            .supportSubunits = Subunits.PT

        CrashReport.initCrashReport(getApplicationContext(), "5b738a86c1", true);
    }
}