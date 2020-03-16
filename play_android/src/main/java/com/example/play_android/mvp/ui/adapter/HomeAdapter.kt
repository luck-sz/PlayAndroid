package com.example.play_android.mvp.ui.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.play_android.R
import com.example.play_android.app.api.entity.ArticleResponse
import com.example.play_android.mvp.ui.view.CollectView

class HomeAdapter(layoutResId: Int, data: MutableList<ArticleResponse>) :
    BaseQuickAdapter<ArticleResponse, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, articleResponse: ArticleResponse) {
        articleResponse.run {
            helper.setText(R.id.item_home_author, if (author.isNotEmpty()) author else shareUser)
            helper.setText(R.id.item_home_title, Html.fromHtml(title))
            helper.setText(R.id.item_home_type, "$superChapterName·$chapterName")
            helper.getView<CollectView>(R.id.item_home_collect).isChecked = collect
            // 时间
            helper.setText(R.id.item_home_data, niceDate)
            // 是否显示新标签
            helper.setGone(R.id.item_home_new, fresh)
            // 是否显示置顶
            helper.setGone(R.id.item_home_top, type == 1)
            // 是否显示问答
            if (tags.isNotEmpty() && tags[0].name != "公众号") {
                helper.setGone(R.id.item_home_ask, true)
                helper.setText(R.id.item_home_ask, tags[0].name)
            } else {
                helper.setGone(R.id.item_home_ask, false)
            }
        }
    }

}
