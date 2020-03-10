package com.example.play_android.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.example.play_android.di.component.DaggerSystemComponent
import com.example.play_android.di.module.SystemModule
import com.example.play_android.mvp.contract.SystemContract
import com.example.play_android.mvp.presenter.SystemPresenter

import com.example.play_android.R
import com.example.play_android.app.base.MySupportFragment
import com.example.play_android.app.event.OpenDrawer
import kotlinx.android.synthetic.main.include_title.*
import kotlinx.android.synthetic.main.include_title_tab.*
import org.simple.eventbus.EventBus


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/11/2020 20:47
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
class SystemFragment : MySupportFragment<SystemPresenter>(), SystemContract.View {
    companion object {
        fun newInstance(): SystemFragment {
            val fragment = SystemFragment()
            return fragment
        }
    }


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
        return inflater.inflate(R.layout.fragment_system, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {
        toolbar_home_tab.run {
            title = "体系"
            inflateMenu(R.menu.menu_activity_home)
            setNavigationOnClickListener {
                EventBus.getDefault().post(OpenDrawer(), "OpenDrawer")
            }
        }
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
}
