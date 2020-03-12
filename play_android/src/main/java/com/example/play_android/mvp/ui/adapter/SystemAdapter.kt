package com.example.play_android.mvp.ui.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.play_android.R
import com.example.play_android.app.api.entity.ArticleResponse
import com.example.play_android.app.api.entity.ClassifyResponse
import com.example.play_android.app.api.entity.SystemResponse
import com.example.play_android.mvp.ui.activity.MainActivity
import com.example.play_android.mvp.ui.activity.randomColor
import com.example.play_android.mvp.ui.activity.showToast
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import kotlinx.android.synthetic.main.flow_layout.view.*

class SystemAdapter(layoutResId: Int, data: MutableList<SystemResponse>) :
    BaseQuickAdapter<SystemResponse, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: SystemResponse) {
        item.let {
            helper.setText(R.id.item_system_title, it.name)
            helper.getView<TagFlowLayout>(R.id.item_system_flow_layout)?.run {
                adapter = object : TagAdapter<ClassifyResponse>(it.children) {
                    override fun getView(
                        parent: FlowLayout?,
                        position: Int,
                        classifyResponse: ClassifyResponse?
                    ): View {
                        return LayoutInflater.from(parent?.context)
                            .inflate(R.layout.flow_layout, this@run, false)
                            .apply {
                                flow_tag.text = Html.fromHtml(classifyResponse?.name)
                                flow_tag.setTextColor(randomColor())
                            }
                    }
                }
                setOnTagClickListener { _, position, _ ->
                    mContext.showToast(it.children[position].name)
                    false
                }
            }
        }
    }

}
