package com.example.play_android.mvp.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.play_android.R
import com.example.play_android.app.api.entity.BannerResponse
import com.zhouwei.mzbanner.holder.MZViewHolder


class BannerViewHolder : MZViewHolder<BannerResponse> {

    private lateinit var mImageView: ImageView

    override fun createView(context: Context): View {
        // 返回页面布局
        val view = LayoutInflater.from(context).inflate(R.layout.item_banner, null)
        mImageView = view.findViewById(R.id.banner_image)
        return view
    }

    override fun onBind(context: Context, position: Int, url: BannerResponse) {
        // 数据绑定
        Glide.with(context)
            .load(url.imagePath)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .into(mImageView)
    }
}
