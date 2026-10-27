package com.yxhelzvl.umsnh_id.features.home.network


import com.yxhelzvl.umsnh_id.core.data.ApiResponse
import com.yxhelzvl.umsnh_id.core.utils.Constants.API_USER
import com.yxhelzvl.umsnh_id.core.utils.Constants.BASE_URL
import com.yxhelzvl.umsnh_id.features.home.network.model.HomeResponse
import com.yxhelzvl.umsnh_id.features.home.network.model.InscripcionByIdEstudianteResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class HomeServiceImpl (private val client: HttpClient): HomeService{
    override suspend fun GetEstudianteById(id_estudiante: String): ApiResponse<HomeResponse> {
        val response = client.get(BASE_URL+API_USER+id_estudiante)
        return response.body< ApiResponse<HomeResponse>>()
    }

}