package com.example.play_android.mvp.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import me.yokeyword.fragmentation.SupportFragment

/**
 * Created by hackware on 2016/9/10.
 */

class SystemTabAdapter(fm: FragmentManager, private val fragments: List<SupportFragment>) :
    FragmentPagerAdapter(fm) {

    override fun getItem(i: Int): Fragment {
        return fragments[i]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}
