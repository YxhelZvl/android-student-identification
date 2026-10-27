package com.yxhelzvl.umsnh_id.features.login.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val idUsuario: Long,
    val nombre: String,
    val apellidoP: String,
    val apellidoM: String,
    val matricula: String,
    val email: String,
    val idRol: Long,
    val status: String
)