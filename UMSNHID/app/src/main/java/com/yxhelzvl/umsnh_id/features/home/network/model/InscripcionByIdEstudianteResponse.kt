package com.yxhelzvl.umsnh_id.features.home.network.model

import kotlinx.serialization.Serializable

@Serializable
data class InscripcionByIdEstudianteResponse(
    val success: Boolean,
    val message: String,
    val data: Data?,
)

