package com.yxhelzvl.umsnh_id.core.data

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val message: String?,
    val data: T?,
    val errors: List<String>?
)