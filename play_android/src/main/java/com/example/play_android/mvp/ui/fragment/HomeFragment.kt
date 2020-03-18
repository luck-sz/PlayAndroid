package com.example.play_android.mvp.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.example.play_android.di.component.DaggerHomeComponent
import com.example.play_android.di.module.HomeModule
import com.example.play_android.mvp.contract.HomeContract
import com.example.play_android.mvp.presenter.HomePresenter

import com.example.play_android.R
import com.example.play_android.app.api.entity.BannerResponse
import com.example.play_android.app.base.MySupportFragment
import com.example.play_android.app.event.OpenDrawer
import com.example.play_android.mvp.ui.activity.SearchActivity
import com.example.play_android.mvp.ui.adapter.HomeAdapter
import com.example.play_android.mvp.ui.view.BannerViewHolder
import com.zhouwei.mzbanner.MZBannerView
import com.zhouwei.mzbanner.holder.MZHolderCreator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_float_btn.*
import kotlinx.android.synthetic.main.include_title.*
import org.simple.eventbus.EventBus

class HomeFragment : MySupportFragment<HomePresenter>(), HomeContract.View {

    lateinit var mBannerView: View
    private lateinit var mMyBanner: MZBannerView<BannerResponse>

    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            return fragment
        }
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .homeModule(HomeModule(this))
            .build()
            .inject(this)
    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        initView()
        mPresenter?.initBanner()
        mPresenter?.getHomePage(0)
    }

    override fun setData(data: Any?) {

    }

    override fun showLoading() {
        refresh_layout.isRefreshing = true
    }

    override fun hideLoading() {
        refresh_layout.isRefreshing = false
    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {

    }

    override fun onPause() {
        super.onPause()
        mMyBanner.pause()
    }

    override fun onResume() {
        super.onResume()
        mMyBanner.start()
    }

    override fun setBanner(banner: List<BannerResponse>) {
        mMyBanner.run {
            setIndicatorVisible(true)
            setDelayedTime(3000)
            duration = 1500
            setPages(banner, MZHolderCreator {
                BannerViewHolder()
            })
        }
        mMyBanner.start()
    }

    override fun addBanner(homeAdapter: HomeAdapter) {
        if (homeAdapter.headerLayoutCount < 1) {
            homeAdapter.addHeaderView(mBannerView)
        }
    }

    override fun setContent(homeAdapter: HomeAdapter) {
        rv_home.run {
            layoutManager = LinearLayoutManager(_mActivity)
            adapter = homeAdapter
            // 监听recycleView滑动到顶部时隐藏floatButton
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                @SuppressLint("RestrictedApi")
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (!canScrollVertically(-1)) {
                        btn_float.visibility = View.INVISIBLE
                    }
                }
            })
        }
    }

    private fun initView() {
        mBannerView = layoutInflater.inflate(R.layout.layout_banner, null, false)
        mMyBanner = mBannerView.findViewById(R.id.banner)
        toolbar_base.run {
            title = "首页"
            inflateMenu(R.menu.menu_activity_home)
            setNavigationOnClickListener {
                EventBus.getDefault().post(OpenDrawer(), "OpenDrawer")
            }
            setOnMenuItemClickListener {
                launchActivity(Intent(_mActivity, SearchActivity::class.java))
                false
            }
        }
        btn_float.run {
            setOnClickListener {
                val layoutManager = rv_home.layoutManager as LinearLayoutManager
                //如果当前recycleView 最后一个视图位置的索引大于等于40，则迅速返回顶部，否则带有滚动动画效果返回到顶部
                if (layoutManager.findLastVisibleItemPosition() >= 40) {
                    rv_home.scrollToPosition(0)//没有动画迅速返回到顶部(马上)
                } else {
                    rv_home.smoothScrollToPosition(0)//有滚动动画返回到顶部(有点慢)
                }
                it.visibility = View.INVISIBLE
            }
        }
        refresh_layout.run {
            setOnRefreshListener {
                mPresenter?.initBanner()
                mPresenter?.getHomePage(0)
            }
        }

    }

}
