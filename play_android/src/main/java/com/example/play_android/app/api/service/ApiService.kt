package com.example.play_android.app.api.service

import com.example.play_android.app.api.entity.ApiPagerResponse
import com.example.play_android.app.api.entity.ApiResponse
import com.example.play_android.app.api.entity.ArticleResponse
import com.example.play_android.app.api.entity.BannerResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {

    /**
     * 获取首页Banner轮播图
     */
    @GET("/banner/json")
    fun getBanner(): Observable<ApiResponse<List<BannerResponse>>>

    /**
     * 获取置顶文章集合数据
     */
    @GET("/article/top/json")
    fun getTopArticleList(): Observable<ApiResponse<List<ArticleResponse>>>
}
