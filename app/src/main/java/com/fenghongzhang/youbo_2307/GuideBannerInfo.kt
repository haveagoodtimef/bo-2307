package com.fenghongzhang.youbo_2307

import com.stx.xhb.androidx.entity.BaseBannerInfo

/**
 * 引导页轮播项，包装本地 drawable 资源 id，供 XBanner 使用。
 */
data class GuideBannerInfo(val resId: Int) : BaseBannerInfo {
    override fun getXBannerUrl(): String = ""
    override fun getXBannerTitle(): String = ""
}
