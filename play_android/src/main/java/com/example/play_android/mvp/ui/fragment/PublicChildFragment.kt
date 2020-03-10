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

import com.example.play_android.di.component.DaggerPublicChildComponent
import com.example.play_android.di.module.PublicChildModule
import com.example.play_android.mvp.contract.PublicChildContract
import com.example.play_android.mvp.presenter.PublicChildPresenter

import com.example.play_android.R
import com.example.play_android.app.base.MySupportFragment


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
