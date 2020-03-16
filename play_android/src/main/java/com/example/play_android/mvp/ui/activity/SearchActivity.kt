package com.example.play_android.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.text.Html
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.ImageView
import com.afollestad.materialdialogs.MaterialDialog
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.example.play_android.di.component.DaggerSearchComponent
import com.example.play_android.di.module.SearchModule
import com.example.play_android.mvp.contract.SearchContract
import com.example.play_android.mvp.presenter.SearchPresenter

import com.example.play_android.R
import com.example.play_android.app.api.entity.SearchResponse
import com.example.play_android.app.base.MySupportActivity
import com.example.play_android.app.utils.ShowUtils
import com.example.play_android.mvp.ui.adapter.HistoryAdapter
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.flow_layout.view.*


class SearchActivity : MySupportActivity<SearchPresenter>(), SearchContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerSearchComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .searchModule(SearchModule(this))
            .build()
            .inject(this)
    }

    var historyData = mutableListOf<String>()//搜索历史数据
    lateinit var adapter: HistoryAdapter//搜索历史适配器

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_search //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {
        toolbar_search.run {
            setSupportActionBar(this)
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                //返回的时候关闭当前界面输入法
                ShowUtils.hideSoftKeyboard(this@SearchActivity)
                finish()
            }
        }
        search_clear.setOnClickListener {
            MaterialDialog(this).show {
                title(text = "温馨提示")
                message(text = "确定清空搜索历史吗？")
                positiveButton(text = "清空") {
                    historyData.clear()
                    adapter.setNewData(historyData)
                }
                negativeButton(R.string.cancel)
            }
        }
        adapter = HistoryAdapter(historyData).apply {
            //设置空布局
            emptyView =
                LayoutInflater.from(this@SearchActivity).inflate(R.layout.search_empty_view, null)
            //删除单个搜索历史
            setOnItemChildClickListener { adapter, _, position ->
                adapter.remove(position)
            }
            //点击了搜索历史的某一个
            setOnItemClickListener { _, _, position ->
                launchActivity(Intent(this@SearchActivity, SearchResultActivity::class.java).apply {
                    putExtra("searchKey", historyData[position])
                })
            }
        }
        search_recycler.run {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            setHasFixedSize(true)
            adapter = this@SearchActivity.adapter
            isNestedScrollingEnabled = false
        }
        mPresenter?.setHotSearch()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_search, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.run {
            maxWidth = Integer.MAX_VALUE
            onActionViewExpanded()
            queryHint = "输入关键字搜索"
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                //SearchView监听
                override fun onQueryTextSubmit(query: String?): Boolean {
                    //当点击搜索时 输入法的搜索，和右边的搜索都会触发
                    query?.let {
                        if (historyData.contains(it)) {
                            //当搜索历史中包含该数据时 删除
                            historyData.remove(it)
                        } else if (historyData.size >= 10) {
                            //如果集合的size 有10个以上了，删除最后一个
                            historyData.removeAt(historyData.size - 1)
                        }
                        launchActivity(
                            Intent(
                                this@SearchActivity,
                                SearchResultActivity::class.java
                            ).apply {
                                putExtra("searchKey", it)
                            })
                        historyData.add(0, it)//添加新数据到第一条
                        this@SearchActivity.adapter.setNewData(historyData)//刷新适配器
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
            isSubmitButtonEnabled = true //右边是否展示搜索图标
            val field = javaClass.getDeclaredField("mGoButton")
            field.run {
                isAccessible = true
                val mGoButton = get(searchView) as ImageView
                mGoButton.setImageResource(R.drawable.ic_search_white)
            }

        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun setHotSearch(hotSearch: MutableList<SearchResponse>) {
        search_flow_layout?.run {
            adapter = object : TagAdapter<SearchResponse>(hotSearch) {
                override fun getView(
                    parent: FlowLayout?,
                    position: Int,
                    searchResponse: SearchResponse?
                ): View {
                    return LayoutInflater.from(parent?.context)
                        .inflate(R.layout.flow_layout, this@run, false)
                        .apply {
                            flow_tag.text = Html.fromHtml(searchResponse?.name)
                            flow_tag.setTextColor(randomColor())
                        }
                }
            }
            setOnTagClickListener { _, position, _ ->
                if (historyData.contains(hotSearch[position].name)) {
                    historyData.remove(hotSearch[position].name)
                } else if (historyData.size >= 10) {
                    historyData.removeAt(historyData.size - 1)
                }
                historyData.add(0, hotSearch[position].name)
                this@SearchActivity.adapter.setNewData(historyData)
                launchActivity(Intent(this@SearchActivity, SearchResultActivity::class.java).apply {
                    putExtra("searchKey", hotSearch[position].name)
                })
                false
            }
        }
    }

}
