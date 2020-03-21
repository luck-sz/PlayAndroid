package com.example.play_android.mvp.presenter

import android.app.Application
import com.example.play_android.R
import com.example.play_android.app.api.entity.ApiPagerResponse
import com.example.play_android.app.api.entity.ArticleResponse
import com.example.play_android.app.api.entity.ClassifyResponse

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.example.play_android.mvp.contract.TreeInfoContract
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
 * Created by MVPArmsTemplate on 03/16/2020 21:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class TreeInfoPresenter
@Inject
constructor(model: TreeInfoContract.Model, rootView: TreeInfoContract.View) :
    BasePresenter<TreeInfoContract.Model, TreeInfoContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    var homeAdapter: HomeAdapter? = null

    fun setTabTitle(position: Int, titles: MutableList<ClassifyResponse>) {
        mRootView.setTabTitle(position, titles)
    }

    fun setContent(pageNo: Int, cid: Int) {
        mModel.getSystemPage(pageNo, cid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                if (pageNo == 0) {
                    mRootView.showLoading()
                }
            }
            .doFinally {
                mRootView.hideLoading()
            }
            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
            .subscribe(object :
                ErrorHandleSubscriber<ApiPagerResponse<MutableList<ArticleResponse>>>(mErrorHandler) {
                override fun onNext(data: ApiPagerResponse<MutableList<ArticleResponse>>) {
                    if (homeAdapter == null) {
                        homeAdapter = HomeAdapter(R.layout.item_article, data.datas)
                        homeAdapter!!.setEnableLoadMore(true)
                        mRootView.setContent(homeAdapter!!)
                    }
                    homeAdapter?.run {
                        if (data.over) {
                            loadMoreEnd()
                        } else {
                            if (pageNo == 0) {
                                // 刷新
                                setNewData(data.datas)
                            } else {
                                addData(data.datas)
                                loadMoreComplete()
                            }
                        }
                    }
                }
            })

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
