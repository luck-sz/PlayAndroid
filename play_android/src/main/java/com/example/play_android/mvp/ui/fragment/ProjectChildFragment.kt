package com.example.play_android.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
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


class ProjectChildFragment : MySupportFragment<ProjectChildPresenter>(), ProjectChildContract.View {
    companion object {
        fun newInstance(): ProjectChildFragment {
            val fragment = ProjectChildFragment()
            return fragment
        }
    }


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
}
