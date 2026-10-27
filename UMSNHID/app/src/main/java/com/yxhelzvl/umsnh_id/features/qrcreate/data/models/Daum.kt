package com.yxhelzvl.umsnh_id.features.qrcreate.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Daum(
    val idInscripcion: Long,
    val nombreUsuario: String,
    val email: String,
    val cicloEscolar: String,
    val fechaInscripcion: String,
    val estado: String,
    val fechaInicioCiclo: String,
    val fechaFinCiclo: String,
)