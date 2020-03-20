package com.example.play_android.mvp.model

import android.app.Application
import com.example.play_android.app.api.Api
import com.example.play_android.app.api.entity.ApiPagerResponse
import com.example.play_android.app.api.entity.ApiResponse
import com.example.play_android.app.api.entity.ArticleResponse
import com.example.play_android.app.api.entity.BannerResponse
import com.example.play_android.app.api.service.ApiService
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.example.play_android.mvp.contract.HomeContract
import io.reactivex.Observable
import io.reactivex.functions.BiFunction


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
class HomeModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager),
    HomeContract.Model {

    @Inject
    lateinit var mGson: Gson
    @Inject
    lateinit var mApplication: Application

    override fun getBanner(): Observable<ApiResponse<List<BannerResponse>>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
            .getBanner()
    }

    override fun getHomePage(pageNo: Int): Observable<MutableList<ArticleResponse>> {
        val mTopArticle: Observable<MutableList<ArticleResponse>> =
            mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getTopArticleList()
                .map { it.data }
        val mArticle: Observable<MutableList<ArticleResponse>> =
            mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getArticleList(pageNo)
                .map { it.data.datas }
        return if (pageNo == 0) {
            // 合并数据
            Observable.zip(mTopArticle, mArticle, BiFunction { list1, list2 ->
                list1.addAll(list2)
                list1
            })
        } else {
            mArticle
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
