package com.example.play_android.mvp.ui.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.play_android.R
import com.example.play_android.app.api.entity.ArticleResponse
import com.example.play_android.mvp.ui.view.CollectView
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils

class ProjectAdapter(layoutResId: Int, data: List<ArticleResponse>) :
    BaseQuickAdapter<ArticleResponse, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, articleResponse: ArticleResponse) {
        articleResponse.run {
            helper.setText(R.id.item_project_author, if (author.isNotEmpty()) author else shareUser)
            helper.setText(R.id.item_project_title, Html.fromHtml(title))
            helper.setText(R.id.item_project_type, "$superChapterName·$chapterName")
            helper.getView<CollectView>(R.id.item_home_collect).isChecked = collect
            // 时间
            helper.setText(R.id.item_project_data, niceDate)
            // 图片
            ArmsUtils.obtainAppComponentFromContext(mContext).imageLoader().loadImage(
                mContext.applicationContext,
                ImageConfigImpl
                    .builder()
                    .url(envelopePic)
                    .imageView(helper.getView(R.id.item_project_img))
                    .errorPic(R.drawable.default_project_img)
                    .fallback(R.drawable.default_project_img)
                    .isCrossFade(true)
                    .build()
            )
            helper.setText(R.id.item_project_content,desc)
        }
    }

}
