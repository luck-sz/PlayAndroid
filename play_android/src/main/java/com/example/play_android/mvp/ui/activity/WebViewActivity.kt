package com.example.play_android.mvp.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.example.play_android.di.component.DaggerWebViewComponent
import com.example.play_android.di.module.WebViewModule
import com.example.play_android.mvp.contract.WebViewContract
import com.example.play_android.mvp.presenter.WebViewPresenter

import com.example.play_android.R
import com.example.play_android.app.api.entity.ArticleResponse
import com.example.play_android.app.api.entity.BannerResponse
import com.example.play_android.app.base.MySupportActivity
import com.just.agentweb.AgentWeb
import com.just.agentweb.LollipopFixedWebView
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.include_title.*
import timber.log.Timber

class WebViewActivity : MySupportActivity<WebViewPresenter>(), WebViewContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerWebViewComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .webViewModule(WebViewModule(this))
            .build()
            .inject(this)
    }

    private lateinit var mAgentWeb: AgentWeb
    lateinit var mTitle: String     // 标题
    lateinit var mUrl: String      // 地址

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_web_view //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        // 从文章点进来的
        intent.getSerializableExtra("data")?.let {
            it as ArticleResponse
            mTitle = it.title
            mUrl = it.link
        }
        // 从轮播图点进来的
        intent.getSerializableExtra("banner")?.let {
            it as BannerResponse
            mTitle = it.title
            mUrl = it.url
        }
        toolbar_base.run {
            setSupportActionBar(this)
            title = Html.fromHtml(mTitle)
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                finish()
            }
        }
        //加载网页
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(web_view_content, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .setWebView(LollipopFixedWebView(this))
            .createAgentWeb()
            .ready()
            .go(mUrl)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.web_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.web_share -> {
                //分享
                startActivity(Intent.createChooser(Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "$mTitle:$mUrl")
                    type = "text/plain"
                }, "分享到"))
            }
            R.id.web_refresh -> {
                //刷新网页
                mAgentWeb.urlLoader.reload()
            }
            R.id.web_collect -> {

            }
            R.id.web_liulanqi -> {
                //用浏览器打开
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mUrl)))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()

    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
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
}
