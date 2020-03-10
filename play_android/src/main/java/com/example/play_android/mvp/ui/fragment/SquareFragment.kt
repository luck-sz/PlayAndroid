package com.example.play_android.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
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
import com.example.play_android.mvp.ui.adapter.HomeAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.refresh_layout
import kotlinx.android.synthetic.main.fragment_square.*
import kotlinx.android.synthetic.main.include_title.*
import org.simple.eventbus.EventBus


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/11/2020 20:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
/**
 * 如果没presenter
 * 你可以这样写
 *
 * @FragmentScope(請注意命名空間) class NullObjectPresenterByFragment
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
class SquareFragment : MySupportFragment<SquarePresenter>(), SquareContract.View {

    companion object {
        fun newInstance(): SquareFragment {
            val fragment = SquareFragment()
            return fragment
        }
    }


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
        toolbar_home.run {
            title = "广场"
            inflateMenu(R.menu.menu_activity_home)
            setNavigationOnClickListener {
                EventBus.getDefault().post(OpenDrawer(), "OpenDrawer")
            }
        }
        refresh_layout.run {
            setOnRefreshListener {
                mPresenter?.initAdapter(0)
            }
        }
        mPresenter?.initAdapter(0)
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
        }
    }
}
