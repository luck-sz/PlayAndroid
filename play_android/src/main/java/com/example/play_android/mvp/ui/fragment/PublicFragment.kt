package com.example.play_android.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.example.play_android.di.component.DaggerPublicComponent
import com.example.play_android.di.module.PublicModule
import com.example.play_android.mvp.contract.PublicContract
import com.example.play_android.mvp.presenter.PublicPresenter

import com.example.play_android.R
import com.example.play_android.app.api.entity.ClassifyResponse
import com.example.play_android.app.base.MySupportFragment
import com.example.play_android.app.event.OpenDrawer
import com.example.play_android.mvp.ui.adapter.PublicTabAdapter
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.fragment_public.*
import kotlinx.android.synthetic.main.include_title_tab.*
import org.simple.eventbus.EventBus

class PublicFragment : MySupportFragment<PublicPresenter>(), PublicContract.View {
    companion object {
        fun newInstance(): PublicFragment {
            val fragment = PublicFragment()
            return fragment
        }
    }

    private var adapter: PublicTabAdapter? = null

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerPublicComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .publicModule(PublicModule(this))
            .build()
            .inject(this)
    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_public, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        toolbar_public_tab.run {
            title = "公众号"
            inflateMenu(R.menu.menu_activity_home)
            setNavigationOnClickListener {
                EventBus.getDefault().post(OpenDrawer(), "OpenDrawer")
            }
        }
        mPresenter?.initTabTitle()

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

    override fun setTabTitle(classIfy: MutableList<ClassifyResponse>) {
        adapter = PublicTabAdapter(childFragmentManager, classIfy)
        vp_content.adapter = adapter
        tab_public.run {
            setOnClickListener {
                vp_content.currentItem = tab_public.currentTab
            }
            setViewPager(vp_content)
        }
    }
}
