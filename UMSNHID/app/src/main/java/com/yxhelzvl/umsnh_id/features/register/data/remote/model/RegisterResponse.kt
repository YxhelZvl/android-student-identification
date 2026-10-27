package com.yxhelzvl.umsnh_id.features.register.data.remote.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.json.JsonElement

@Serializable
data class RegisterResponse(
    val success: Boolean?,
    val timestamp: String,
    val status: Long,
    val message: String,
    val data: JsonElement?,
    val errors: List<String?>,
)