package com.example.play_android.mvp.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.play_android.app.api.entity.ClassifyResponse
import com.example.play_android.mvp.ui.fragment.ProjectChildFragment

class ProjectTabAdapter(
    fm: FragmentManager,
    private val list: MutableList<ClassifyResponse>
) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return ProjectChildFragment.newInstance(list[position].id)
    }

    override fun getPageTitle(position: Int): CharSequence? = list[position].name

    override fun getCount(): Int = list.size

}
