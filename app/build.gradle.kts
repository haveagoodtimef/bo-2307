plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp") version "1.9.24-1.0.20"
    id("com.google.dagger.hilt.android")
}


android {
    namespace = "com.fenghongzhang.youbo_2307"
    compileSdk = 35
    
    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.fenghongzhang.youbo_2307"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        manifestPlaceholders += mapOf(
            "JPUSH_PKGNAME" to "com.fenghongzhang.youbo_2307",
            "JPUSH_APPKEY" to "0c552f12fc36ba103dbb1a2e",
            "JPUSH_CHANNEL" to "default_developer"
        )


    }


    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation("cn.jiguang.sdk:jpush:5.5.3")
    implementation("com.tencent.bugly:crashreport:4.1.9.3")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.viewpager2:viewpager2:1.0.0")


    implementation("com.google.dagger:hilt-android:2.55")
    ksp("com.google.dagger:hilt-android-compiler:2.55")

    // MVVM架构相关依赖
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.fragment.ktx)
    
    // 网络请求相关
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    
    // 协程相关
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    
    // 图片加载
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)

    // 引导页轮播与按钮
    implementation("com.github.xiaohaibin:XBanner:androidx_v1.2.6")

    implementation("com.github.JessYanCoding:AndroidAutoSize:v1.2.1")

    // Lottie 动画
    implementation("com.airbnb.android:lottie:6.7.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}