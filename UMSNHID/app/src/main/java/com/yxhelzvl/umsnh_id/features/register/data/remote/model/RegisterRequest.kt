package com.yxhelzvl.umsnh_id.features.register.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val nombre: String,
    val apellidoP: String,
    val apellidoM: String,
    val matricula: String,
    val email: String,
    val contraseña: String,
    val idRol: Long,
    val idCarrera: Int
)