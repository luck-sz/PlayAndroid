package com.example.play_android.mvp.presenter

import android.app.Application
import com.example.play_android.app.ResponseErrorListenerImpl
import com.example.play_android.app.api.entity.ApiResponse
import com.example.play_android.app.api.entity.UserInfoResponse

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.example.play_android.mvp.contract.LoginContract
import com.jess.arms.utils.RxLifecycleUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import java.lang.Exception


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/16/2020 20:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class LoginPresenter
@Inject
constructor(model: LoginContract.Model, rootView: LoginContract.View) :
    BasePresenter<LoginContract.Model, LoginContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    fun login(username: String, password: String) {
        mModel.login(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
            .subscribe(object :
                ErrorHandleSubscriber<ApiResponse<UserInfoResponse>>(mErrorHandler) {
                override fun onNext(response: ApiResponse<UserInfoResponse>) {
                    mRootView.onSuccess(response.data)
                }
            })
    }

    fun register(username: String, password: String, password1: String) {
        mModel.register(username, password, password1)
            .subscribeOn(Schedulers.io())
            .flatMap {
                //转换，如果注册成功，直接调起登录，失败则跑出异常
                if (it.errorCode != -1) {
                    mModel.login(username, password)
                } else {
                    throw Exception(it.errorMsg)
                }
            }
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
            .subscribe(object :
                ErrorHandleSubscriber<ApiResponse<UserInfoResponse>>(mErrorHandler) {
                override fun onNext(response: ApiResponse<UserInfoResponse>) {
                    if (response.errorCode != -1) {
                        mRootView.onSuccess(response.data)
                    } else {
                        mRootView.showMessage(response.errorMsg)
                    }
                }
            })

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
