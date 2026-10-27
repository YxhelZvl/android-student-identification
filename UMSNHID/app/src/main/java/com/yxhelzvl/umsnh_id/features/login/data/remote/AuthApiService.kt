package com.yxhelzvl.umsnh_id.features.login.data.remote

import com.yxhelzvl.umsnh_id.core.data.ApiResponse
import com.yxhelzvl.umsnh_id.features.login.data.models.LoginResponse

interface AuthApiService {
    suspend fun login(username: String, password: String): ApiResponse<LoginResponse>
}