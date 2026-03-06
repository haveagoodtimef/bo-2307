package com.fenghongzhang.youbo_2307.ui.youbo.featured

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.fenghongzhang.youbo_2307.databinding.ItemFeaturedBannerBinding
import com.fenghongzhang.youbo_2307.model.CarouselItem

/**
 * 精选页多布局 Adapter
 * 当前仅支持头部 Banner 类型
 */
class FeaturedAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_BANNER = 0
    }

    var list: List<FeaturedListItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int = when (list[position]) {
        is FeaturedListItem.Banner -> VIEW_TYPE_BANNER
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_BANNER -> {
                val binding = ItemFeaturedBannerBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                BannerViewHolder(binding)
            }
            else -> throw IllegalArgumentException("unknown viewType=$viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = list[position]) {
            is FeaturedListItem.Banner -> (holder as BannerViewHolder).bind(item.items)
        }
    }

    class BannerViewHolder(
        private val binding: ItemFeaturedBannerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(items: List<CarouselItem>) {
            val adapter = BannerSlideAdapter(items) //使用viewpage，实现了一个banner的效果
            binding.bannerPager.adapter = adapter
            binding.bannerDots.visibility = if (items.size > 1) android.view.View.VISIBLE else android.view.View.GONE
            if (items.size > 1) {
                TabLayoutMediator(binding.bannerDots, binding.bannerPager) { _, _ -> }.attach()
            }
        }
    }
}
