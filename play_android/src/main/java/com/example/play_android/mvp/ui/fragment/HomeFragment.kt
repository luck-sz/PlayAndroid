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

import com.example.play_android.di.component.DaggerHomeComponent
import com.example.play_android.di.module.HomeModule
import com.example.play_android.mvp.contract.HomeContract
import com.example.play_android.mvp.presenter.HomePresenter

import com.example.play_android.R
import com.example.play_android.app.api.entity.BannerResponse
import com.example.play_android.app.base.MySupportFragment
import com.example.play_android.mvp.ui.adapter.HomeAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : MySupportFragment<HomePresenter>(), HomeContract.View {

    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            return fragment
        }
    }


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .homeModule(HomeModule(this))
            .build()
            .inject(this)
    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {
//        mPresenter?.initBanner()
        mPresenter?.initAdapter()
    }

    override fun setData(data: Any?) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {

    }

    override fun setBanner(banner: List<BannerResponse>) {

    }

    override fun addBanner() {

    }

    override fun setContent(homeAdapter: HomeAdapter) {
        rv_home.run {
            layoutManager = LinearLayoutManager(_mActivity)
            adapter = homeAdapter
        }
    }

}
