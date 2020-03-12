package com.example.play_android.mvp.model

import android.app.Application
import com.example.play_android.app.api.entity.NavigationResponse
import com.example.play_android.app.api.service.ApiService
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.FragmentScope
import javax.inject.Inject

import com.example.play_android.mvp.contract.NavigationContract
import io.reactivex.Observable


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
class NavigationModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager),
    NavigationContract.Model {
    @Inject
    lateinit var mGson: Gson
    @Inject
    lateinit var mApplication: Application

    override fun getNavigationData(): Observable<MutableList<NavigationResponse>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
            .getNavigationData()
            .map {
                it.data
            }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
