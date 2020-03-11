package com.example.play_android.app.api.service

import com.example.play_android.app.api.entity.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    /**
     * 获取公众号数据
     */
    @GET("/wxarticle/list/{id}/{page}/json")
    fun getPublicData(@Path("page") pageNo: Int, @Path("id") id: Int): Observable<ApiResponse<ApiPagerResponse<MutableList<ArticleResponse>>>>

    /**
     * 项目分类
     */
    @GET("/project/tree/json")
    fun getProjectTypes(): Observable<ApiResponse<MutableList<ClassifyResponse>>>

    /**
     * 根据分类id获取项目数据
     */
    @GET("/project/list/{page}/json")
    fun getProjectDataByType(@Path("page") pageNo: Int, @Query("cid") cid: Int): Observable<ApiResponse<ApiPagerResponse<MutableList<ArticleResponse>>>>
}
