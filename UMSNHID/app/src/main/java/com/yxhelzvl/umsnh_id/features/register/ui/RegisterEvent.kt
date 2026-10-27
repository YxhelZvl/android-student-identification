package com.yxhelzvl.umsnh_id.features.register.ui

sealed class RegisterEvent {
    data class SetName(val name: String) : RegisterEvent()
    data class SetPaternalSurname(val surname: String) : RegisterEvent()
    data class SetMaternalSurname(val surname: String) : RegisterEvent()
    data class SetMatricula(val matricula: String) : RegisterEvent()
    data class SetEmail(val email: String) : RegisterEvent()
    data class SetPassword(val password: String) : RegisterEvent()
    data class SelectCareer(val career: String) : RegisterEvent()
    object NavigateToLogin: RegisterEvent()
    object TogglePasswordVisibility : RegisterEvent()
    object Submit : RegisterEvent()
    object ToggleDropdown : RegisterEvent()
    object ChangeDialogAlert : RegisterEvent()
}