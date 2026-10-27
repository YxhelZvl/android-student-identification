package com.yxhelzvl.umsnh_id.features.home.network

import com.yxhelzvl.umsnh_id.core.data.ApiResponse
import com.yxhelzvl.umsnh_id.features.home.network.model.HomeResponse
import com.yxhelzvl.umsnh_id.features.home.network.model.InscripcionByIdEstudianteResponse


class HomeRepositoryImpl(private val service: HomeService): HomeRepository {
    override suspend fun EstudianteById(id_estudiante: String): ApiResponse<HomeResponse> {
        return service.GetEstudianteById(id_estudiante)
    }


}