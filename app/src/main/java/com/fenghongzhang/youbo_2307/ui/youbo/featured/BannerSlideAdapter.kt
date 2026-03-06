package com.fenghongzhang.youbo_2307.ui.youbo.featured

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fenghongzhang.youbo_2307.databinding.ItemFeaturedBannerSlideBinding
import com.fenghongzhang.youbo_2307.model.CarouselItem

/**
 * Banner 内部 ViewPager2 的图片适配器
 */
class BannerSlideAdapter(
    private val items: List<CarouselItem>
) : RecyclerView.Adapter<BannerSlideAdapter.SlideViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        val binding = ItemFeaturedBannerSlideBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SlideViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        val item = items.getOrNull(position)
        holder.bind(item)
    }

    class SlideViewHolder(
        private val binding: ItemFeaturedBannerSlideBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CarouselItem?) {
            if (item == null) return
            Glide.with(binding.ivBanner.context)
                .load(item.ad_code)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(binding.ivBanner)
        }
    }
}
