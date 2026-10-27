package com.yxhelzvl.umsnh_id.features.register.data.remote

import android.os.Build
import androidx.annotation.RequiresApi
import com.yxhelzvl.umsnh_id.core.utils.Constants.API_REGISTRAR
import com.yxhelzvl.umsnh_id.core.utils.Constants.BASE_URL
import com.yxhelzvl.umsnh_id.features.register.data.remote.model.RegisterRequest
import com.yxhelzvl.umsnh_id.features.register.data.remote.model.RegisterResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter // Añade esta importación

class RegisterServiceImpl (private val client: HttpClient) : RegisterService{
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun register(request: RegisterRequest): RegisterResponse {
        return try {
            val response = client.post(BASE_URL+API_REGISTRAR) {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            response.body()
        } catch (e: Exception) {
            val currentTimestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
            RegisterResponse(
                success = false,
                timestamp = currentTimestamp,
                status = 500, // Or another appropriate error status code
                message = e.message ?: "Error desconocido",
                data = null,
                errors = listOf(e.message)
            )
        }
    }

}