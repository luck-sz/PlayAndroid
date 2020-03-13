package com.example.play_android.mvp.model

import android.app.Application
import com.example.play_android.app.api.entity.ApiResponse
import com.example.play_android.app.api.entity.SearchResponse
import com.example.play_android.app.api.service.ApiService
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.example.play_android.mvp.contract.SearchContract
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/13/2020 13:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class SearchModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager),
    SearchContract.Model {
    @Inject
    lateinit var mGson: Gson
    @Inject
    lateinit var mApplication: Application

    override fun getHotSearch(): Observable<MutableList<SearchResponse>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
            .getSearchData()
            .map {
                it.data
            }

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
