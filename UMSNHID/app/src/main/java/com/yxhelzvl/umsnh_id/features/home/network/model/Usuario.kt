package com.yxhelzvl.umsnh_id.features.home.network.model

import kotlinx.serialization.Serializable


@Serializable
data class Usuario(
    val idUsuario: Long,
    val nombre: String,
    val apellidoP: String,
    val apellidoM: String,
    val matricula: String,
    val email: String,
    val password: String,
    val idRol: Long,
    val status: String,
    val tipoRol: String,
)