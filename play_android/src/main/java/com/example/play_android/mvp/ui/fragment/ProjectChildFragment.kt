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

import com.example.play_android.di.component.DaggerProjectChildComponent
import com.example.play_android.di.module.ProjectChildModule
import com.example.play_android.mvp.contract.ProjectChildContract
import com.example.play_android.mvp.presenter.ProjectChildPresenter

import com.example.play_android.R
import com.example.play_android.app.base.MySupportFragment
import com.example.play_android.mvp.ui.adapter.HomeAdapter
import com.example.play_android.mvp.ui.adapter.ProjectAdapter
import kotlinx.android.synthetic.main.fragment_project_child.*


class ProjectChildFragment : MySupportFragment<ProjectChildPresenter>(), ProjectChildContract.View {
    companion object {
        fun newInstance(cid: Int): ProjectChildFragment {
            val args = Bundle()
            args.putInt("cid", cid)
            val fragment = ProjectChildFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var cid: Int = 0

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerProjectChildComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .projectChildModule(ProjectChildModule(this))
            .build()
            .inject(this)
    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_project_child, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {
        cid = arguments?.getInt("cid") ?: 0
        mPresenter?.getProjectData(0, cid)
        refresh_layout_project.run {
            setOnRefreshListener {
                mPresenter?.getProjectData(0, cid)
            }
        }
    }

    override fun setData(data: Any?) {

    }

    override fun showLoading() {
        refresh_layout_project.isRefreshing = true
    }

    override fun hideLoading() {
        refresh_layout_project.isRefreshing = false
    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {

    }

    override fun setContent(projectAdapter: ProjectAdapter) {
        rv_project.run {
            layoutManager = LinearLayoutManager(_mActivity)
            adapter = projectAdapter
        }
    }
}
