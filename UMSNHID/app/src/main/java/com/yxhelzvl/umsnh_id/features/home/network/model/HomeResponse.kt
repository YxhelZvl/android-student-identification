package com.yxhelzvl.umsnh_id.features.home.network.model

import kotlinx.serialization.Serializable

@Serializable
data class HomeResponse(
    val usuario: Usuario,
    val info: Info
)