package com.yxhelzvl.umsnh_id.features.login.data.models

import kotlinx.serialization.Serializable


@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)