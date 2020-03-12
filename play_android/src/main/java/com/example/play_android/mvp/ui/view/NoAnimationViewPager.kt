package com.example.play_android.mvp.ui.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet

/**
 * Created by apk2sf on 2017/12/2.
 * email: apk2sf@163.com
 * QQ：337081267
 */

class NoAnimationViewPager : ViewPager {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, smoothScroll)
    }

    override fun setCurrentItem(item: Int) {
        //去除页面切换时的滑动翻页效果
        super.setCurrentItem(item, false)
    }
}

