package com.example.play_android.mvp.ui.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.play_android.R
import com.example.play_android.app.api.entity.ArticleResponse
import com.example.play_android.app.api.entity.ClassifyResponse
import com.example.play_android.app.api.entity.NavigationResponse
import com.example.play_android.app.api.entity.SystemResponse
import com.example.play_android.mvp.ui.activity.MainActivity
import com.example.play_android.mvp.ui.activity.randomColor
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import kotlinx.android.synthetic.main.flow_layout.view.*

class NavigationAdapter(layoutResId: Int, data: MutableList<NavigationResponse>) :
    BaseQuickAdapter<NavigationResponse, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: NavigationResponse) {
        item.let {
            helper.setText(R.id.item_system_title, it.name)
            helper.getView<TagFlowLayout>(R.id.item_system_flow_layout)?.run {
                adapter = object : TagAdapter<ArticleResponse>(it.articles) {
                    override fun getView(
                        parent: FlowLayout?,
                        position: Int,
                        articleResponse: ArticleResponse?
                    ): View {
                        return LayoutInflater.from(parent?.context)
                            .inflate(R.layout.flow_layout, this@run, false)
                            .apply {
                                flow_tag.text = Html.fromHtml(articleResponse?.title)
                                flow_tag.setTextColor(randomColor())
                            }
                    }
                }
            }
        }
    }

}
