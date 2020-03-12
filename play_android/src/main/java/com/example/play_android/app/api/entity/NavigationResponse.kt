package com.example.play_android.app.api.entity

import java.io.Serializable

/**
 * 导航数据
 */
data class NavigationResponse(
    var articles: MutableList<ArticleResponse>,
    var cid: Int,
    var name: String
) : Serializable
