package com.example.play_android.mvp.presenter

import android.app.Application
import com.example.play_android.R
import com.example.play_android.app.api.entity.ArticleResponse

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.example.play_android.mvp.contract.PublicChildContract
import com.example.play_android.mvp.ui.activity.showToast
import com.example.play_android.mvp.ui.adapter.HomeAdapter
import com.jess.arms.utils.RxLifecycleUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/10/2020 14:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
class PublicChildPresenter
@Inject
constructor(model: PublicChildContract.Model, rootView: PublicChildContract.View) :
    BasePresenter<PublicChildContract.Model, PublicChildContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    var homeAdapter: HomeAdapter? = null

    fun getPublicData(pageNo: Int, id: Int) {
        mModel.getPublicData(pageNo, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                mRootView.showLoading()
            }
            .doFinally {
                mRootView.hideLoading()
            }
            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
            .subscribe(object : ErrorHandleSubscriber<MutableList<ArticleResponse>>(mErrorHandler) {
                override fun onNext(data: MutableList<ArticleResponse>) {
                    if (homeAdapter == null){
                        homeAdapter = HomeAdapter(R.layout.item_article, data)
                    }
                    // 刷新
                    homeAdapter!!.setNewData(data)
                    mRootView.setContent(homeAdapter!!)
                    homeAdapter?.run {
                        setOnItemClickListener { _, _, position ->
                            mApplication.showToast(data[position].title)
                        }
                    }
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
