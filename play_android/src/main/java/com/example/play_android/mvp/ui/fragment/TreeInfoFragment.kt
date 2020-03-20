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

import com.example.play_android.di.component.DaggerTreeInfoComponent
import com.example.play_android.di.module.TreeInfoModule
import com.example.play_android.mvp.contract.TreeInfoContract
import com.example.play_android.mvp.presenter.TreeInfoPresenter

import com.example.play_android.R
import com.example.play_android.app.api.entity.ClassifyResponse
import com.example.play_android.app.base.MySupportFragment
import com.example.play_android.mvp.ui.adapter.HomeAdapter
import kotlinx.android.synthetic.main.fragment_tree_info.*

class TreeInfoFragment : MySupportFragment<TreeInfoPresenter>(), TreeInfoContract.View {
    companion object {
        fun newInstance(cid: Int): TreeInfoFragment {
            val args = Bundle()
            val fragment = TreeInfoFragment()
            args.putInt("cid", cid)
            fragment.arguments = args
            return fragment
        }
    }

    private var cid: Int = 0
    private var pageNo: Int = 0

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerTreeInfoComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .treeInfoModule(TreeInfoModule(this))
            .build()
            .inject(this)
    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_tree_info, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {
        cid = arguments?.getInt("cid") ?: 0
        mPresenter?.setContent(pageNo, cid)
        refresh_layout_tree_info.run {
            setOnRefreshListener {
                pageNo = 0
                mPresenter?.setContent(pageNo, cid)
            }
        }
    }

    override fun setData(data: Any?) {

    }

    override fun showLoading() {
        refresh_layout_tree_info.isRefreshing = true
    }

    override fun hideLoading() {
        refresh_layout_tree_info.isRefreshing = false
    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {

    }

    override fun setTabTitle(position: Int, titles: MutableList<ClassifyResponse>) {

    }

    override fun setContent(homeAdapter: HomeAdapter) {
        rv_tree_info.run {
            layoutManager = LinearLayoutManager(_mActivity)
            adapter = homeAdapter
        }
        homeAdapter.setOnLoadMoreListener({
            pageNo++
            mPresenter?.setContent(pageNo, cid)
        }, rv_tree_info)
    }
}
