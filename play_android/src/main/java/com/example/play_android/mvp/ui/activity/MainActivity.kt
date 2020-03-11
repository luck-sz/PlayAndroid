package com.example.play_android.mvp.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.example.play_android.di.component.DaggerMainComponent
import com.example.play_android.di.module.MainModule
import com.example.play_android.mvp.contract.MainContract
import com.example.play_android.mvp.presenter.MainPresenter
import com.example.play_android.R
import com.example.play_android.app.base.MySupportActivity
import com.example.play_android.app.event.OpenDrawer
import com.example.play_android.mvp.ui.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.include_title.*
import kotlinx.android.synthetic.main.main_content.*
import me.yokeyword.fragmentation.SupportFragment
import org.simple.eventbus.EventBus
import org.simple.eventbus.Subscriber
import java.util.*

// 扩展方法
fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

/**
 * 获取随机rgb颜色值
 */
fun randomColor(): Int {
    Random().run {
        //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        val red = nextInt(190)
        val green = nextInt(190)
        val blue = nextInt(190)
        //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red, green, blue)
    }
}

class MainActivity : MySupportActivity<MainPresenter>(), MainContract.View {

    // 存放切换页的Fragment数组
    private val mFragments = arrayOfNulls<SupportFragment>(5)
    private var mExitTime: Long = 0

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .mainModule(MainModule(this))
            .build()
            .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {
        initFragment()
        initDrawerLayout()
        initBottomNav()
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

    /**
     * 连续点击退出App
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                showToast(getString(R.string.exit_tip))
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * init DrawerLayout
     */
    private fun initDrawerLayout() {
        drawer_layout.run {
            val toggle = ActionBarDrawerToggle(
                this@MainActivity,
                this,
                toolbar_home
                , R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            addDrawerListener(toggle)
            toggle.syncState()
        }
    }

    /**
     * 初始化fragment
     */
    private fun initFragment() {
        val homeFragment = findFragment(HomeFragment::class.java)
        if (homeFragment == null) {
            mFragments[0] = HomeFragment.newInstance()//主页
            mFragments[1] = ProjectFragment.newInstance()//项目
            mFragments[2] = PublicFragment.newInstance()//公众号
            mFragments[3] = SystemFragment.newInstance()//体系
            mFragments[4] = SquareFragment.newInstance()//广场
            loadMultipleRootFragment(
                R.id.frame_content, 0, mFragments[0]
                , mFragments[1], mFragments[2], mFragments[3], mFragments[4]
            )
        } else {
            mFragments[0] = homeFragment
            mFragments[1] = findFragment(ProjectFragment::class.java)
            mFragments[2] = findFragment(PublicFragment::class.java)
            mFragments[3] = findFragment(SystemFragment::class.java)
            mFragments[4] = findFragment(SquareFragment::class.java)
        }
    }

    @Subscriber(tag = "OpenDrawer")
    fun openDrawer(openDrawer: OpenDrawer) {
        drawer_layout.openDrawer(Gravity.START)
    }

    /**
     * 初始化底部切换导航栏
     */
    private fun initBottomNav() {
        bottomNav.run {
            currentItem = 0
            enableAnimation(false)
            enableShiftingMode(false)
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_main -> {
                        showHideFragment(mFragments[0])
                    }
                    R.id.nav_project -> {
                        showHideFragment(mFragments[1])
                    }
                    R.id.nav_public -> {
                        showHideFragment(mFragments[2])
                    }
                    R.id.nav_system -> {
                        showHideFragment(mFragments[3])
                    }
                    R.id.nav_square -> {
                        showHideFragment(mFragments[4])
                    }
                }
                true
            }
        }
    }

}
