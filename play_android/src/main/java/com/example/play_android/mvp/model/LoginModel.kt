package com.example.play_android.mvp.model

import android.app.Application
import com.example.play_android.app.api.entity.ApiResponse
import com.example.play_android.app.api.entity.UserInfoResponse
import com.example.play_android.app.api.service.ApiService
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.example.play_android.mvp.contract.LoginContract
import io.reactivex.Observable


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
class LoginModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager),
    LoginContract.Model {
    @Inject
    lateinit var mGson: Gson
    @Inject
    lateinit var mApplication: Application

    override fun login(username: String, password: String): Observable<ApiResponse<UserInfoResponse>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
            .login(username, password)

    }

    override fun register(
        username: String,
        password: String,
        password1: String
    ): Observable<ApiResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
            .register(username, password, password1)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
