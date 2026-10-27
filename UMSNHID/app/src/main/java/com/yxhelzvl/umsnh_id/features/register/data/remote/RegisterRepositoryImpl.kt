package com.yxhelzvl.umsnh_id.features.register.data.remote

import com.yxhelzvl.umsnh_id.features.register.data.remote.model.RegisterRequest
import com.yxhelzvl.umsnh_id.features.register.data.remote.model.RegisterResponse

class RegisterRepositoryImpl(private val service: RegisterService): RegisterRepository {
    override suspend fun registerEstudiante(request: RegisterRequest): RegisterResponse {
        return service.register(request)
    }

}