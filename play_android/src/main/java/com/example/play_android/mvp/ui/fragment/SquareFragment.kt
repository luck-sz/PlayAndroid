package com.example.play_android.mvp.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.example.play_android.di.component.DaggerSquareComponent
import com.example.play_android.di.module.SquareModule
import com.example.play_android.mvp.contract.SquareContract
import com.example.play_android.mvp.presenter.SquarePresenter

import com.example.play_android.R
import com.example.play_android.app.base.MySupportFragment
import com.example.play_android.app.event.OpenDrawer
import com.example.play_android.mvp.ui.activity.WebViewActivity
import com.example.play_android.mvp.ui.activity.showToast
import com.example.play_android.mvp.ui.adapter.HomeAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.refresh_layout
import kotlinx.android.synthetic.main.fragment_square.*
import kotlinx.android.synthetic.main.include_float_btn.*
import kotlinx.android.synthetic.main.include_title.*
import org.simple.eventbus.EventBus

class SquareFragment : MySupportFragment<SquarePresenter>(), SquareContract.View {

    companion object {
        fun newInstance(): SquareFragment {
            val fragment = SquareFragment()
            return fragment
        }
    }

    private var pageNo: Int = 0

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerSquareComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .squareModule(SquareModule(this))
            .build()
            .inject(this)
    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_square, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {
        toolbar_base.run {
            title = "广场"
            inflateMenu(R.menu.menu_activity_square)
            setNavigationOnClickListener {
                EventBus.getDefault().post(OpenDrawer(), "OpenDrawer")
            }
        }
        refresh_layout.run {
            setOnRefreshListener {
                pageNo = 0
                mPresenter?.initAdapter(pageNo)
            }
        }
        btn_float.run {
            setOnClickListener {
                val layoutManager = rv_square.layoutManager as LinearLayoutManager
                //如果当前recycleView 最后一个视图位置的索引大于等于40，则迅速返回顶部，否则带有滚动动画效果返回到顶部
                if (layoutManager.findLastVisibleItemPosition() >= 40) {
                    rv_square.scrollToPosition(0)//没有动画迅速返回到顶部(马上)
                } else {
                    rv_square.smoothScrollToPosition(0)//有滚动动画返回到顶部(有点慢)
                }
                it.visibility = View.INVISIBLE
            }
        }
        mPresenter?.initAdapter(pageNo)
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

    override fun setAdapter(homeAdapter: HomeAdapter) {
        rv_square.run {
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
        homeAdapter.run {
            setOnLoadMoreListener({
                pageNo++
                mPresenter?.initAdapter(pageNo)
            }, rv_square)
            setOnItemClickListener { _, _, position ->
                val intent = Intent(_mActivity, WebViewActivity::class.java)
                val bundle = Bundle().also {
                    it.putSerializable("data", data[position])
                }
                intent.putExtras(bundle)
                launchActivity(intent)
            }
        }
    }
}
