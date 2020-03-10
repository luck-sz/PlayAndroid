package com.example.play_android.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.example.play_android.di.component.DaggerSystemComponent
import com.example.play_android.di.module.SystemModule
import com.example.play_android.mvp.contract.SystemContract
import com.example.play_android.mvp.presenter.SystemPresenter
import com.example.play_android.R
import com.example.play_android.app.base.MySupportFragment
import com.example.play_android.app.event.OpenDrawer
import com.example.play_android.mvp.ui.adapter.SystemTabAdapter
import kotlinx.android.synthetic.main.fragment_system.*
import me.yokeyword.fragmentation.SupportFragment
import org.simple.eventbus.EventBus


class SystemFragment : MySupportFragment<SystemPresenter>(), SystemContract.View {
    companion object {
        fun newInstance(): SystemFragment {
            val fragment = SystemFragment()
            return fragment
        }
    }

    private var adapter: SystemTabAdapter? = null
    private var fragments: MutableList<SupportFragment> = mutableListOf()

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerSystemComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .systemModule(SystemModule(this))
            .build()
            .inject(this)
    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_system, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        toolbar_system.run {
            title = "体系"
            inflateMenu(R.menu.menu_activity_home)
            setNavigationOnClickListener {
                EventBus.getDefault().post(OpenDrawer(), "OpenDrawer")
            }
        }
        mPresenter?.setTitle()
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

    override fun setTabTitle(titles: Array<String>) {
        fragments.run {
            add(NavigationFragment.newInstance())
            add(NavigationFragment.newInstance())
        }
        adapter = SystemTabAdapter(childFragmentManager, fragments)
        vp_content_system.adapter = adapter
        tab_system.run {
            setViewPager(vp_content_system, titles)
            currentTab = 0
        }
    }
}
