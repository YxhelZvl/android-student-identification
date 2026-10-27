package com.yxhelzvl.umsnh_id.features.home.network

import com.yxhelzvl.umsnh_id.core.data.ApiResponse
import com.yxhelzvl.umsnh_id.features.home.network.model.HomeResponse
import com.yxhelzvl.umsnh_id.features.home.network.model.InscripcionByIdEstudianteResponse

interface HomeService {
    suspend fun GetEstudianteById(id_estudiante: String): ApiResponse<HomeResponse>
}