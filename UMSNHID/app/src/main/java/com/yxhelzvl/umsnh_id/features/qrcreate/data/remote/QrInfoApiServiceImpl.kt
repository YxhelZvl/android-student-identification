package com.yxhelzvl.umsnh_id.features.qrcreate.data.remote

import com.yxhelzvl.umsnh_id.core.data.ApiResponse
import com.yxhelzvl.umsnh_id.core.utils.Constants.API_INSCRIPCION
import com.yxhelzvl.umsnh_id.core.utils.Constants.API_LOGIN
import com.yxhelzvl.umsnh_id.core.utils.Constants.API_USER
import com.yxhelzvl.umsnh_id.core.utils.Constants.BASE_URL
import com.yxhelzvl.umsnh_id.features.home.network.model.HomeResponse
import com.yxhelzvl.umsnh_id.features.login.data.models.LoginRequest
import com.yxhelzvl.umsnh_id.features.qrcreate.data.models.QrCreateResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class QrInfoApiServiceImpl (private val client: HttpClient) : QrInfoApiService {
    override suspend fun QrInfoUsuario(id_usuario: String): ApiResponse<QrCreateResponse> {
        val response = client.get(BASE_URL+API_INSCRIPCION+id_usuario)
        return response.body< ApiResponse<QrCreateResponse>>()
    }

}