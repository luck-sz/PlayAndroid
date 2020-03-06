package com.example.play_android.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.play_android.R
import com.example.play_android.app.api.entity.ArticleResponse

class HomeAdapter(layoutResId: Int, data: List<ArticleResponse>) :
    BaseQuickAdapter<ArticleResponse, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, articleResponse: ArticleResponse) {
        articleResponse.run {
            helper.setText(R.id.tv_title, title)
        }
    }

}
