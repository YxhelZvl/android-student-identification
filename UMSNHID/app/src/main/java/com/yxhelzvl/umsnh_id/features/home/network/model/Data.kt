package com.yxhelzvl.umsnh_id.features.home.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val nombre: String,
    val apellidoP: String,
    val apellidoM: String,
    val matricula: String,
    val email: String,
    val carrera: String,
    val facultad: String,
    val ciclo: String,
    val fecha_inicio: String,
    val fecha_final: String,
    val fecha_inscripcion: String,
    val estado: String,
)