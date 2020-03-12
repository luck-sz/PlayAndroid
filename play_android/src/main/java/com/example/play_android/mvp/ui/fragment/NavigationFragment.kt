package com.example.play_android.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.example.play_android.di.component.DaggerNavigationComponent
import com.example.play_android.di.module.NavigationModule
import com.example.play_android.mvp.contract.NavigationContract
import com.example.play_android.mvp.presenter.NavigationPresenter

import com.example.play_android.R
import com.example.play_android.app.base.MySupportFragment
import com.example.play_android.mvp.ui.adapter.NavigationAdapter
import kotlinx.android.synthetic.main.fragment_navigation.*


class NavigationFragment : MySupportFragment<NavigationPresenter>(), NavigationContract.View {
    companion object {
        fun newInstance(): NavigationFragment {
            val fragment = NavigationFragment()
            return fragment
        }
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerNavigationComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .navigationModule(NavigationModule(this))
            .build()
            .inject(this)
    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_navigation, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {
        mPresenter?.getNavigationData()
        refresh_layout_navigation.run {
            setOnRefreshListener {
                mPresenter?.getNavigationData()
            }
        }
    }

    override fun setData(data: Any?) {

    }

    override fun showLoading() {
        refresh_layout_navigation.isRefreshing = true
    }

    override fun hideLoading() {
        refresh_layout_navigation.isRefreshing = false
    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {

    }

    override fun setContent(navigationAdapter: NavigationAdapter) {
        rv_navigation.run {
            layoutManager = LinearLayoutManager(_mActivity)
            adapter = navigationAdapter
        }
    }
}
