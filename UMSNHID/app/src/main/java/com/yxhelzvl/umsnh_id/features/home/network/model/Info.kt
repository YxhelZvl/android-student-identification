package com.yxhelzvl.umsnh_id.features.home.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Info(
    val idCarrera: Long,
    val carrera: String,
    val facultad: String,
)