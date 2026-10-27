package com.yxhelzvl.umsnh_id.features.register.ui

data class RegisterState(
    val name: String = "",
    val paternalSurname: String = "",
    val maternalSurname: String = "",
    val matricula: String = "",
    val email: String = "",
    val password: String = "",
    val selectedCareer: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val msg: String? = null,
    val isRegistrationSuccessful: Boolean = false,
    val isRegistrationNotSuccessful: Boolean = false,
    val isRegistration: Boolean = false,
    val careerOptions: List<String> = listOf(
        "Ingeniería Eléctrica",
        "Ingeniería Electrónica",
        "Ingeniería en Computación",
    ), // Opciones de carrera
    val isDropdownExpanded: Boolean = false, // Estado del dropdown
    val openDialog: Boolean = false
)