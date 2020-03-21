package com.example.play_android.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.example.play_android.di.component.DaggerSearchResultComponent
import com.example.play_android.di.module.SearchResultModule
import com.example.play_android.mvp.contract.SearchResultContract
import com.example.play_android.mvp.presenter.SearchResultPresenter

import com.example.play_android.R
import com.example.play_android.app.base.MySupportActivity
import com.example.play_android.mvp.ui.adapter.HomeAdapter
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.include_title.*


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/13/2020 16:22
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
 * @ActivityScope(請注意命名空間) class NullObjectPresenterByActivity
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
class SearchResultActivity : MySupportActivity<SearchResultPresenter>(), SearchResultContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerSearchResultComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .searchResultModule(SearchResultModule(this))
            .build()
            .inject(this)
    }

    lateinit var searchKey: String//搜索关键词
    private var pageNo: Int = 0

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_search_result //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        searchKey = intent.getStringExtra("searchKey")
        toolbar_base.run {
            title = searchKey
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                finish()
            }
        }
        refresh_layout_result.run {
            setOnRefreshListener {
                pageNo = 0
                mPresenter?.getResult(pageNo, searchKey)
            }
        }
        mPresenter?.getResult(pageNo, searchKey)
    }

    override fun showLoading() {
        refresh_layout_result.isRefreshing = true
    }

    override fun hideLoading() {
        refresh_layout_result.isRefreshing = false
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

    override fun setContent(homeAdapter: HomeAdapter) {
        rv_search_result.run {
            layoutManager = LinearLayoutManager(this@SearchResultActivity)
            adapter = homeAdapter
        }
        homeAdapter.run {
            setOnLoadMoreListener({
                pageNo++
                mPresenter?.getResult(pageNo, searchKey)
            }, rv_search_result)
            setOnItemClickListener { _, _, position ->
                val intent = Intent(this@SearchResultActivity, WebViewActivity::class.java)
                val bundle = Bundle().also {
                    it.putSerializable("data", data[position])
                }
                intent.putExtras(bundle)
                launchActivity(intent)
            }
        }
    }
}
