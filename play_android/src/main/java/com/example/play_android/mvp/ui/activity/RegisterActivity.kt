package com.example.play_android.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View

import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.example.play_android.R
import com.example.play_android.app.api.entity.UserInfoResponse
import com.example.play_android.app.base.MySupportActivity
import com.example.play_android.app.utils.CacheUtil
import com.example.play_android.di.component.DaggerLoginComponent
import com.example.play_android.di.module.LoginModule
import com.example.play_android.mvp.contract.LoginContract
import com.example.play_android.mvp.presenter.LoginPresenter
import com.jess.arms.integration.AppManager
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.include_title.*
import org.simple.eventbus.EventBus

class RegisterActivity : MySupportActivity<LoginPresenter>(), LoginContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .loginModule(LoginModule(this))
            .build()
            .inject1(this)
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
        register_username.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    register_clear.visibility = View.VISIBLE
                } else {
                    register_clear.visibility = View.GONE
                }
            }
        })
        register_pwd.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    register_key.visibility = View.VISIBLE
                } else {
                    register_key.visibility = View.GONE
                }
            }
        })
        register_pwd1.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    register_key1.visibility = View.VISIBLE
                } else {
                    register_key1.visibility = View.GONE
                }
            }
        })
        register_key.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                register_pwd.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                register_pwd.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            register_pwd.setSelection(register_pwd.text.toString().length)
        }
        register_key1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                register_pwd1.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                register_pwd1.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            register_pwd1.setSelection(register_pwd1.text.toString().length)
        }
        register_clear.setOnClickListener {
            register_username.setText("")
        }
        register_sub.setOnClickListener {
            when {
                register_username.text.isEmpty() -> showMessage("请填写账号")
                register_username.text.length < 6 -> showMessage("账号长度不能小于6位")
                register_pwd.text.isEmpty() -> showMessage("请填写密码")
                register_pwd.text.length < 6 -> showMessage("密码长度不能小于6位")
                register_pwd1.text.isEmpty() -> showMessage("请填写确认密码")
                register_pwd1.text.toString() != register_pwd.text.toString() -> showMessage("密码不一致")
                else -> mPresenter?.register(
                    register_username.text.toString(),
                    register_pwd.text.toString(),
                    register_pwd1.text.toString()
                )
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

    override fun onSuccess(userInfo: UserInfoResponse) {
        showToast("登录成功...")
        CacheUtil.setUser(userInfo)//保存账户信息
        AppManager.getAppManager().killActivity(LoginActivity::class.java)
        EventBus.getDefault().post(userInfo, "LoginSuccess")
        finish()
    }
}
