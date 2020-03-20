package com.example.play_android.mvp.presenter

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import com.example.play_android.R
import com.example.play_android.app.api.entity.ApiPagerResponse
import com.example.play_android.app.api.entity.ApiResponse
import com.example.play_android.app.api.entity.ArticleResponse
import com.example.play_android.app.api.entity.BannerResponse

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.example.play_android.mvp.contract.HomeContract
import com.example.play_android.mvp.ui.activity.showToast
import com.example.play_android.mvp.ui.adapter.HomeAdapter
import com.jess.arms.utils.RxLifecycleUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import timber.log.Timber
import java.util.*


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/11/2020 14:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class HomePresenter
@Inject
constructor(model: HomeContract.Model, rootView: HomeContract.View) :
    BasePresenter<HomeContract.Model, HomeContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    var homeAdapter: HomeAdapter? = null

    fun initBanner() {
        mModel.getBanner()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
            .subscribe(object :
                ErrorHandleSubscriber<ApiResponse<List<BannerResponse>>>(mErrorHandler) {
                override fun onNext(response: ApiResponse<List<BannerResponse>>) {
                    mRootView.setBanner(response.data)
                }
            })

    }

    // 根据页数获取首页数据(为0时也获取首页数据)
    fun getHomePage(pageNo: Int) {
        mModel.getHomePage(pageNo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                if (pageNo == 0) mRootView.showLoading()
            }
            .doFinally {
                mRootView.hideLoading()
            }
            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
            .subscribe(object : ErrorHandleSubscriber<MutableList<ArticleResponse>>(mErrorHandler) {
                override fun onNext(data: MutableList<ArticleResponse>) {
                    if (homeAdapter == null) {
                        homeAdapter = HomeAdapter(R.layout.item_article, data)
                        homeAdapter!!.setEnableLoadMore(true)
                        mRootView.addBanner(homeAdapter!!)
                        mRootView.setContent(homeAdapter!!)
                    }

                    homeAdapter?.run {
                        if (pageNo == 0) {
                            // 刷新
                            setNewData(data)
                        } else {
                            addData(data)
                            loadMoreComplete()
                        }
                        setOnItemClickListener { _, _, position ->
                            mApplication.showToast(data[position].title)
                        }
                    }
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        homeAdapter = null
    }
}
