package com.yxhelzvl.umsnh_id.features.home.ui

import com.yxhelzvl.umsnh_id.features.register.ui.RegisterEvent

sealed class HomeEvent() {
    data class SetError(val error: String?) : HomeEvent()
    data class SetId_Estudiante(val id_student: String) : HomeEvent()
    object LoadDataStudent : HomeEvent()
    object NavigateToGenerarQr : HomeEvent()
    object NavigateToEscanearQr : HomeEvent()
    object NavigateToPrestamosActivos : HomeEvent() // Nuevo
    object NavigateToGestionPrestamos : HomeEvent() // Nuevo para administradores
    object ResetCerrarSesion : HomeEvent()
    object CerrarSesion : HomeEvent()
    object NavigateToLogin : HomeEvent()
    object LoadActiveLoans : HomeEvent() // Nuevo para cargar préstamos
    object NavigateToBiblioteca : HomeEvent()
    object NavigateToBack : HomeEvent()
}

