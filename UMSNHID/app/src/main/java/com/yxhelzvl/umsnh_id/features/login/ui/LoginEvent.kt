package com.yxhelzvl.umsnh_id.features.login.ui

sealed class LoginEvent{
    data class SetUsername(val username: String) : LoginEvent()
    data class SetPassword(val password: String) : LoginEvent()
    object TogglePasswordVisibility : LoginEvent()
    object Login : LoginEvent()
    object LoginWithGoogle : LoginEvent()
    object NavigateToHome: LoginEvent()
    object NavigateToRegister: LoginEvent()
    object ClearErrors: LoginEvent()
    object ClearIdUsuario: LoginEvent()
    object ClearNavigateToHome: LoginEvent()
    object ChangeDialogAlert: LoginEvent()
}