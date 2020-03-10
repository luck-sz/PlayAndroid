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

import com.example.play_android.di.component.DaggerPublicChildComponent
import com.example.play_android.di.module.PublicChildModule
import com.example.play_android.mvp.contract.PublicChildContract
import com.example.play_android.mvp.presenter.PublicChildPresenter

import com.example.play_android.R
import com.example.play_android.app.base.MySupportFragment
import com.example.play_android.mvp.ui.adapter.HomeAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_public_child.*
import kotlinx.android.synthetic.main.fragment_public_child.refresh_layout


class PublicChildFragment : MySupportFragment<PublicChildPresenter>(), PublicChildContract.View {
    companion object {
        fun newInstance(id: Int): PublicChildFragment {
            val args = Bundle()
            args.putInt("cid", id)
            val fragment = PublicChildFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var cid: Int = 0

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerPublicChildComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .publicChildModule(PublicChildModule(this))
            .build()
            .inject(this)
    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_public_child, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {
        cid = arguments?.getInt("cid") ?: 0
        mPresenter?.getPublicData(0, cid)
        refresh_layout.run {
            setOnRefreshListener {
                mPresenter?.getPublicData(0, id = cid)
            }
        }
    }

    override fun setData(data: Any?) {

    }

    override fun showLoading() {
        refresh_layout.isRefreshing = true
    }

    override fun hideLoading() {
        refresh_layout.isRefreshing = false
    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {

    }

    override fun setContent(homeAdapter: HomeAdapter) {
        rv_public.run {
            layoutManager = LinearLayoutManager(_mActivity)
            adapter = homeAdapter
        }
    }
}
