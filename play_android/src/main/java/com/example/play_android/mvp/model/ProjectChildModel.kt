package com.example.play_android.mvp.model

import android.app.Application
import com.example.play_android.app.api.entity.ArticleResponse
import com.example.play_android.app.api.service.ApiService
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.FragmentScope
import javax.inject.Inject

import com.example.play_android.mvp.contract.ProjectChildContract
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/10/2020 19:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
class ProjectChildModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager),
    ProjectChildContract.Model {
    @Inject
    lateinit var mGson: Gson
    @Inject
    lateinit var mApplication: Application

    override fun getProjectByType(pageNo: Int, cid: Int): Observable<MutableList<ArticleResponse>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
            .getProjectDataByType(pageNo, cid)
            .map {
                it.data.datas
            }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
