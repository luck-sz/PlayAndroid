package com.example.play_android.mvp.presenter

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

                override fun onError(t: Throwable) {
                }
            })

    }

    fun initAdapter() {
        mModel.getTopArticle()
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
                ErrorHandleSubscriber<ApiResponse<List<ArticleResponse>>>(mErrorHandler) {
                override fun onNext(response: ApiResponse<List<ArticleResponse>>) {
                    // 第一次创建时
                    if (homeAdapter == null) {
                        homeAdapter = HomeAdapter(R.layout.item_article, response.data)
                        mRootView.addBanner(homeAdapter!!)
                    }
                    // 刷新
                    homeAdapter!!.setNewData(response.data)
                    mRootView.setContent(homeAdapter!!)
                }

                override fun onError(t: Throwable) {

                }
            })
    }

    fun initData(pageNo: Int) {
        var data: MutableList<ArticleResponse>
        mModel.getArticle(pageNo)
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
                ErrorHandleSubscriber<ApiResponse<ApiPagerResponse<MutableList<ArticleResponse>>>>(
                    mErrorHandler
                ) {
                override fun onNext(response: ApiResponse<ApiPagerResponse<MutableList<ArticleResponse>>>) {
                    data = response.data.datas
                    // 如果请求的是第一页则加上请求置顶文章
                    if (pageNo == 0) {
                        mModel.getTopArticle()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                            .subscribe(object :
                                ErrorHandleSubscriber<ApiResponse<List<ArticleResponse>>>(
                                    mErrorHandler
                                ) {
                                override fun onNext(response: ApiResponse<List<ArticleResponse>>) {
                                    data.addAll(0, response.data)
                                }
                            })
                    }
                    // 第一次创建时
                    if (homeAdapter == null) {
                        homeAdapter = HomeAdapter(R.layout.item_article, data)
                        mRootView.addBanner(homeAdapter!!)
                    }
                    // 刷新
                    homeAdapter!!.setNewData(data)
                    mRootView.setContent(homeAdapter!!)
                }
            })

    }

    override fun onDestroy() {
        super.onDestroy()
        homeAdapter = null
    }
}
