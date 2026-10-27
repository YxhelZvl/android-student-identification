package com.yxhelzvl.umsnh_id.features.login.data.remote

import android.os.Build
import androidx.annotation.RequiresApi
import com.yxhelzvl.umsnh_id.core.data.ApiResponse
import com.yxhelzvl.umsnh_id.core.utils.Constants.API_LOGIN
import com.yxhelzvl.umsnh_id.core.utils.Constants.API_REGISTRAR
import com.yxhelzvl.umsnh_id.core.utils.Constants.BASE_URL
import com.yxhelzvl.umsnh_id.features.home.network.model.InscripcionByIdEstudianteResponse
import com.yxhelzvl.umsnh_id.features.login.data.models.LoginRequest
import com.yxhelzvl.umsnh_id.features.login.data.models.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AuthApiServiceImpl(private val client: HttpClient) : AuthApiService {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun login(username: String, password: String): ApiResponse<LoginResponse> {
        val response = client.post(BASE_URL + API_LOGIN) {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(username, password))
            }
        return response.body()
    }
}

/*
return try {
    val response = client.post(BASE_URL + API_REGISTRAR) {
        contentType(ContentType.Application.Json)
        setBody(LoginRequest(username, password))
    }
    response.body()
}catch (e:Exception){
    println("Error en Implementacion")
    val currentTimestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
    ApiResponse(
        success = false,
        message = e.message ?: "Error desconocido",
        data = null,
        errors = listOf(e.message ?: "Error Al autentificarse/iniciar sesion")
    )

}*/