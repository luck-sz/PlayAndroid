package com.example.play_android.app.api.service

import com.example.play_android.app.api.entity.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

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
    fun getTopArticleList(): Observable<ApiResponse<MutableList<ArticleResponse>>>

    /**
     * 获取首页文章数据
     */
    @GET("/article/list/{page}/json")
    fun getArticleList(@Path("page") pageNo: Int): Observable<ApiResponse<ApiPagerResponse<MutableList<ArticleResponse>>>>

    /**
     * 获取广场列表数据
     */
    @GET("/user_article/list/{page}/json")
    fun getSquareList(@Path("page") pageNo: Int): Observable<ApiResponse<ApiPagerResponse<MutableList<ArticleResponse>>>>

    /**
     * 公众号分类
     */
    @GET("/wxarticle/chapters/json")
    fun getPublicTypes(): Observable<ApiResponse<MutableList<ClassifyResponse>>>
}
