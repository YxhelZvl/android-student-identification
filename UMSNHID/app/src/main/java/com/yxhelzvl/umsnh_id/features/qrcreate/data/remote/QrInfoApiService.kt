package com.yxhelzvl.umsnh_id.features.qrcreate.data.remote

import com.yxhelzvl.umsnh_id.core.data.ApiResponse
import com.yxhelzvl.umsnh_id.features.qrcreate.data.models.QrCreateResponse

interface QrInfoApiService {
    suspend fun QrInfoUsuario(id_usuario: String) : ApiResponse<QrCreateResponse>
}