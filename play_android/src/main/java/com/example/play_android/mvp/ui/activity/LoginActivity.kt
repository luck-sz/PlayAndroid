package com.example.play_android.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.example.play_android.di.component.DaggerLoginComponent
import com.example.play_android.di.module.LoginModule
import com.example.play_android.mvp.contract.LoginContract
import com.example.play_android.mvp.presenter.LoginPresenter

import com.example.play_android.R
import com.example.play_android.app.api.entity.UserInfoResponse
import com.example.play_android.app.base.MySupportActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.include_title.*


class LoginActivity : MySupportActivity<LoginPresenter>(), LoginContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .loginModule(LoginModule(this))
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_login //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        toolbar_base.run {
            title = "登录"
            setNavigationIcon(R.drawable.ic_close)
            setNavigationOnClickListener {
                finish()
            }
        }
        // 是否显示清除用户名按键
        login_username.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    login_clear.visibility = View.VISIBLE
                } else {
                    login_clear.visibility = View.GONE
                }
            }
        })
        // 是否显示隐藏密码按键
        login_pwd.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    login_key.visibility = View.VISIBLE
                } else {
                    login_key.visibility = View.GONE
                }
            }
        })
        // 隐藏密码按键点击事件
        login_key.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                login_pwd.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                login_pwd.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            login_pwd.setSelection(login_pwd.text.toString().length)
        }
        login_go_register.setOnClickListener {
            launchActivity(Intent(this, RegisterActivity::class.java))
        }
        // 登录
        login_sub.setOnClickListener {
            when {
                login_username.text.isEmpty() -> showToast("请填写账号")
                login_pwd.text.isEmpty() -> showToast("请填写密码")
                else -> mPresenter?.login(login_username.text.toString(), login_pwd.text.toString())
            }
        }
        // 清除用户名
        login_clear.setOnClickListener {
            login_username.setText("")
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

    override fun loginSuccess(userInfo: UserInfoResponse) {
        showToast("登录成功...")
        finish()
    }

    override fun loginFail() {
        showToast("登录失败...")
    }
}
