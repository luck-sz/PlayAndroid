package com.example.play_android.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.play_android.R

class HistoryAdapter(data: MutableList<String>?) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_history, data) {

    override fun convert(helper: BaseViewHolder, item: String?) {
        item?.let {
            helper.setText(R.id.item_history_text,it)
            helper.addOnClickListener(R.id.item_history_del)
        }
    }

}