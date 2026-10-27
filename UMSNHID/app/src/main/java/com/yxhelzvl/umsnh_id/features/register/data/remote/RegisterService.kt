package com.yxhelzvl.umsnh_id.features.register.data.remote

import com.yxhelzvl.umsnh_id.features.register.data.remote.model.RegisterRequest
import com.yxhelzvl.umsnh_id.features.register.data.remote.model.RegisterResponse

interface RegisterService {
    suspend fun register(request: RegisterRequest): RegisterResponse
}