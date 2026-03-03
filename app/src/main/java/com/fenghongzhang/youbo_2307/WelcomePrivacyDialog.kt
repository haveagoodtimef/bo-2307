package com.fenghongzhang.youbo_2307

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.fenghongzhang.youbo_2307.databinding.DialogWelcomePrivacyBinding

/**
 * 欢迎页：用户协议和隐私政策弹窗
 * - 展示协议与隐私说明，支持《有播用户协议》《隐私保护指引》链接点击
 * - “不同意并退出”关闭弹窗并结束 Activity
 * - “同意并继续”关闭弹窗并回调 onAgreed（可由调用方持久化已同意状态）
 */
class WelcomePrivacyDialog : DialogFragment() {

    private var _binding: DialogWelcomePrivacyBinding? = null
    private val binding get() = _binding!!

    var onAgreed: (() -> Unit)? = null
    var onDisagreed: (() -> Unit)? = null

    /** 用户协议链接（可替换为实际 H5 地址） */
    var userAgreementUrl: String = "https://example.com/user-agreement"

    /** 隐私保护指引链接（可替换为实际 H5 地址） */
    var privacyUrl: String = "https://example.com/privacy"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
        setStyle(STYLE_NORMAL, R.style.WelcomePrivacyDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogWelcomePrivacyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupContentWithClickableLinks()
        binding.btnAgree.setOnClickListener {
            dismiss()
            onAgreed?.invoke()
        }
        binding.btnDisagree.setOnClickListener {
            dismiss()
            onDisagreed?.invoke()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupContentWithClickableLinks() {
        val section1 = getString(R.string.welcome_section_user_agreement)
        val part1 = getString(R.string.welcome_content_part1)
        val part2 = getString(R.string.welcome_content_part2)
        val item1 = getString(R.string.welcome_item_1)
        val item2 = getString(R.string.welcome_item_2)
        val item3 = getString(R.string.welcome_item_3)
        val linkUser = getString(R.string.link_user_agreement)
        val linkPrivacy = getString(R.string.link_privacy)

        val fullText = buildString {
            append(section1)
            append("\n\n")
            append(part1)
            append("\n\n")
            append(part2)
            append("\n\n")
            append(item1)
            append("\n\n")
            append(item2)
            append("\n\n")
            append(item3)
        }

        val spannable = SpannableString(fullText)
        val linkColor = requireContext().getColor(R.color.link_blue)

        fun makeClickable(linkText: String, onClick: () -> Unit) {
            val start = fullText.indexOf(linkText)
            if (start >= 0) {
                spannable.setSpan(
                    object : ClickableSpan() {
                        override fun onClick(widget: View) = onClick()
                        override fun updateDrawState(ds: TextPaint) {
                            ds.color = linkColor
                            ds.isUnderlineText = true
                        }
                    },
                    start,
                    start + linkText.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        makeClickable(linkUser) { openUrl(userAgreementUrl) }
        makeClickable(linkPrivacy) { openUrl(privacyUrl) }

        binding.tvContent.text = spannable
        binding.tvContent.movementMethod = LinkMovementMethod.getInstance()
        binding.tvContent.highlightColor = Color.TRANSPARENT
    }

    private fun openUrl(url: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: Exception) {
            // 无浏览器时可 Toast 或降级处理
        }
    }

    companion object {
        const val TAG = "WelcomePrivacyDialog"

        fun newInstance(): WelcomePrivacyDialog = WelcomePrivacyDialog()
    }
}
