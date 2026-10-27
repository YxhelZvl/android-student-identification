package com.yxhelzvl.umsnh_id.features.login.ui

data class LoginState(
    val username: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val navigateToHome: Boolean = false,
    val id_usuario: String? = null,
    val openDialog: Boolean = false
)