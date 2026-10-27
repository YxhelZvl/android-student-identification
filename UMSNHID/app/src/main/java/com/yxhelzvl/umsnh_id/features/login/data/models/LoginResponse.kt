package com.yxhelzvl.umsnh_id.features.login.data.models

import com.yxhelzvl.umsnh_id.features.login.models.User
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(val token: String, val usuario: User)