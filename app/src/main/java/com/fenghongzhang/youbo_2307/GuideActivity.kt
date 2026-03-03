package com.fenghongzhang.youbo_2307

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.fenghongzhang.youbo_2307.R
import com.stx.xhb.androidx.XBanner
import androidx.viewpager.widget.ViewPager

/**
 * 引导页：XBanner 轮播四张图，最后一页显示「即刻体验」按钮，点击后结束并返回 RESULT_OK。
 */
class GuideActivity : AppCompatActivity() {

    private val bannerList = listOf(
        GuideBannerInfo(R.drawable.guide_one),
        GuideBannerInfo(R.drawable.guide_two),
        GuideBannerInfo(R.drawable.guide_three),
        GuideBannerInfo(R.drawable.guide_for)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        val xbanner = findViewById<XBanner>(R.id.xbanner)
        val tvNext = findViewById<Button>(R.id.tv_next)

        xbanner.setBannerData(bannerList)
        xbanner.loadImage(object : XBanner.XBannerAdapter {
            override fun loadBanner(banner: XBanner?, model: Any?, view: View?, position: Int) {
                val resId = (model as? GuideBannerInfo)?.resId ?: return
                when (view) {
                    is ImageView -> Glide.with(this@GuideActivity).load(resId).into(view)
                    else -> (view as? ImageView)?.setImageResource(resId)
                }
            }
        })

        xbanner.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                val realPosition = position % bannerList.size
                tvNext.visibility = if (realPosition == bannerList.size - 1) View.VISIBLE else View.INVISIBLE
            }
        })
        tvNext.visibility = View.INVISIBLE

        tvNext.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun onDestroy() {
       // findViewById<XBanner>(R.id.xbanner)?.releaseBanner()
        super.onDestroy()
    }
}
