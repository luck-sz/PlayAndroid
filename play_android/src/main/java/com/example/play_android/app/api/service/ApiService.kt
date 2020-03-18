package com.example.play_android.app.api.service

import com.example.play_android.app.api.entity.*
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("/user/login")
    fun login(@Field("username") username: String, @Field("password") pwd: String): Observable<ApiResponse<UserInfoResponse>>

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

    /**
     * 获取体系标签数据
     */
    @GET("/tree/json")
    fun getSystemData(): Observable<ApiResponse<MutableList<SystemResponse>>>

    /**
     * 知识体系下的文章数据
     */
    @GET("/article/list/{page}/json")
    fun getSystemDataByTree(@Path("page") pageNo: Int, @Query("cid") cid: Int): Observable<ApiResponse<ApiPagerResponse<MutableList<ArticleResponse>>>>

    /**
     * 获取导航标签数据
     */
    @GET("/navi/json")
    fun getNavigationData(): Observable<ApiResponse<MutableList<NavigationResponse>>>

    /**
     * 获取热门搜索数据
     */
    @GET("/hotkey/json")
    fun getSearchData(): Observable<ApiResponse<MutableList<SearchResponse>>>

    /**
     * 根据关键词搜索数据
     */
    @POST("/article/query/{page}/json")
    fun getSearchDataByKey(@Path("page") pageNo: Int, @Query("k") searchKey: String): Observable<ApiResponse<ApiPagerResponse<MutableList<ArticleResponse>>>>
}
