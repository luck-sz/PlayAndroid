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

import com.example.play_android.di.component.DaggerSystemChildComponent
import com.example.play_android.di.module.SystemChildModule
import com.example.play_android.mvp.contract.SystemChildContract
import com.example.play_android.mvp.presenter.SystemChildPresenter

import com.example.play_android.R
import com.example.play_android.app.base.MySupportFragment
import com.example.play_android.mvp.ui.adapter.SystemAdapter
import kotlinx.android.synthetic.main.fragment_system_child.*

class SystemChildFragment : MySupportFragment<SystemChildPresenter>(), SystemChildContract.View {
    companion object {
        fun newInstance(): SystemChildFragment {
            val fragment = SystemChildFragment()
            return fragment
        }
    }


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerSystemChildComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .systemChildModule(SystemChildModule(this))
            .build()
            .inject(this)
    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_system_child, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {
        mPresenter?.getSystemData()
        refresh_layout_system_child.run {
            setOnRefreshListener {
                mPresenter?.getSystemData()
            }
        }
    }

    override fun setData(data: Any?) {

    }

    override fun showLoading() {
        refresh_layout_system_child.isRefreshing = true
    }

    override fun hideLoading() {
        refresh_layout_system_child.isRefreshing = false
    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {

    }

    override fun setContent(systemAdapter: SystemAdapter) {
        rv_system_child.run {
            layoutManager = LinearLayoutManager(_mActivity)
            adapter = systemAdapter
        }
    }
}
