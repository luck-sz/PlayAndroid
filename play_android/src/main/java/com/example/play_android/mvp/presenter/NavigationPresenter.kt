package com.example.play_android.mvp.presenter

import android.app.Application
import com.example.play_android.R
import com.example.play_android.app.api.entity.NavigationResponse

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.example.play_android.mvp.contract.NavigationContract
import com.example.play_android.mvp.ui.adapter.NavigationAdapter
import com.jess.arms.utils.RxLifecycleUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/10/2020 20:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
class NavigationPresenter
@Inject
constructor(model: NavigationContract.Model, rootView: NavigationContract.View) :
    BasePresenter<NavigationContract.Model, NavigationContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    private var adapter: NavigationAdapter? = null

    fun getNavigationData() {
        mModel.getNavigationData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                mRootView.showLoading()
            }
            .doFinally {
                mRootView.hideLoading()
            }
            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
            .subscribe(object :
                ErrorHandleSubscriber<MutableList<NavigationResponse>>(mErrorHandler) {
                override fun onNext(data: MutableList<NavigationResponse>) {
                    if (adapter == null) {
                        adapter = NavigationAdapter(R.layout.item_system, data)
                    }
                    adapter!!.setNewData(data)
                    mRootView.setContent(adapter!!)
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
