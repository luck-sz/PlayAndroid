package com.example.play_android.app.api.entity

import java.io.Serializable

/**
 *  体系数据
 */
data class SystemResponse(
    var children: MutableList<ClassifyResponse>,
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int
) : Serializable
