package com.fenghongzhang.youbo_2307

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

/**
 * 欢迎页（仅启动一次）
 * 顺序：先用户协议与隐私政策弹窗，同意后再判断引导页（引导页只出现一次），最后进入首页 MainActivity。
 */
class WelcomeActivity : AppCompatActivity() {

    private val guideLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            setGuideSeen(true)
            startHomeAndFinish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isPrivacyAgreed()) {
            setContentView(R.layout.activity_welcome)
            showWelcomePrivacyDialog()
            return
        }
        // 已同意隐私，再判断是否需要进入引导页（只出现一次）
        if (!isGuideSeen()) {
            startGuideActivity()
            return
        }
        startHomeAndFinish()
    }

    private fun isPrivacyAgreed(): Boolean {
        return getSharedPreferences(PREFS_WELCOME, MODE_PRIVATE).getBoolean(KEY_PRIVACY_AGREED, false)
    }

    private fun setPrivacyAgreed(agreed: Boolean) {
        getSharedPreferences(PREFS_WELCOME, MODE_PRIVATE).edit()
            .putBoolean(KEY_PRIVACY_AGREED, agreed)
            .apply()
    }

    private fun isGuideSeen(): Boolean {
        return getSharedPreferences(PREFS_WELCOME, MODE_PRIVATE).getBoolean(KEY_GUIDE_SEEN, false)
    }

    private fun setGuideSeen(seen: Boolean) {
        getSharedPreferences(PREFS_WELCOME, MODE_PRIVATE).edit()
            .putBoolean(KEY_GUIDE_SEEN, seen)
            .apply()
    }

    private fun showWelcomePrivacyDialog() {
        val dialog = WelcomePrivacyDialog.newInstance()
        dialog.onAgreed = {
            setPrivacyAgreed(true)
            tryContinueAfterPrivacy()
        }
        dialog.onDisagreed = { finish() }
        dialog.show(supportFragmentManager, WelcomePrivacyDialog.TAG)
    }

    /** 隐私同意后：未看过引导页则进入 GuideActivity，否则直接进首页 */
    private fun tryContinueAfterPrivacy() {
        if (!isGuideSeen()) {
            startGuideActivity()
            return
        }
        startHomeAndFinish()
    }

    private fun startGuideActivity() {
        guideLauncher.launch(Intent(this, GuideActivity::class.java))
    }

    private fun startHomeAndFinish() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    companion object {
        private const val PREFS_WELCOME = "welcome"
        private const val KEY_PRIVACY_AGREED = "privacy_agreed"
        private const val KEY_GUIDE_SEEN = "guide_seen"
    }
}
