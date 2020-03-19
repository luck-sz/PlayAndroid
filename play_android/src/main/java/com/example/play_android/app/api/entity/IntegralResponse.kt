package com.example.play_android.app.api.entity

import java.io.Serializable

/**
 * 积分
 */
data class IntegralResponse(
    var coinCount: Int,//当前积分
    var level: Int,
    var rank: Int,
    var userId: Int,
    var username: String
) : Serializable


