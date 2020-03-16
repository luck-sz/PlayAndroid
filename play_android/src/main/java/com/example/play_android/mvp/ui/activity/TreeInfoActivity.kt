package com.example.play_android.mvp.ui.activity

import android.content.Intent
import android.os.Bundle

import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.example.play_android.di.component.DaggerTreeInfoComponent
import com.example.play_android.di.module.TreeInfoModule
import com.example.play_android.mvp.contract.TreeInfoContract
import com.example.play_android.mvp.presenter.TreeInfoPresenter

import com.example.play_android.R
import com.example.play_android.app.api.entity.ClassifyResponse
import com.example.play_android.app.api.entity.SystemResponse
import com.example.play_android.app.base.MySupportActivity
import com.example.play_android.mvp.ui.adapter.HomeAdapter
import com.example.play_android.mvp.ui.adapter.TreeInfoTabAdapter
import kotlinx.android.synthetic.main.activity_tree_info.*
import kotlinx.android.synthetic.main.fragment_project.*
import timber.log.Timber

class TreeInfoActivity : MySupportActivity<TreeInfoPresenter>(), TreeInfoContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerTreeInfoComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .treeInfoModule(TreeInfoModule(this))
            .build()
            .inject(this)
    }

    lateinit var systemBean: SystemResponse
    var position: Int = 0
    lateinit var mAdapter: TreeInfoTabAdapter

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_tree_info //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        systemBean = intent.getSerializableExtra("systemBean") as SystemResponse
        position = intent.getIntExtra("position", 0)
        toolbar_tree_info.run {
            title = systemBean.name
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                finish()
            }
        }
        mPresenter?.setTabTitle(position, systemBean.children)
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

    override fun setTabTitle(position: Int, titles: MutableList<ClassifyResponse>) {
        mAdapter = TreeInfoTabAdapter(supportFragmentManager, titles)
        vp_content_tree_info.run {
            adapter = mAdapter
            currentItem = position
        }
        tab_tree_info.run {
            setViewPager(vp_content_tree_info)
            setOnClickListener {
                vp_content_tree_info.currentItem = tab_tree_info.currentTab
            }
        }
    }

    override fun setContent(homeAdapter: HomeAdapter) {

    }
}
