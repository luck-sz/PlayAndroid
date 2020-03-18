package com.example.play_android.mvp.ui.activity

import android.content.Intent
import android.os.Bundle

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.example.play_android.di.component.DaggerRegisterComponent
import com.example.play_android.di.module.RegisterModule
import com.example.play_android.mvp.contract.RegisterContract
import com.example.play_android.mvp.presenter.RegisterPresenter

import com.example.play_android.R
import com.example.play_android.app.base.MySupportActivity
import kotlinx.android.synthetic.main.include_title.*

class RegisterActivity : MySupportActivity<RegisterPresenter>(), RegisterContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerRegisterComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .registerModule(RegisterModule(this))
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_register //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        toolbar_base.run {
            title = "注册"
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                finish()
            }
        }
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
        finish()
    }
}
