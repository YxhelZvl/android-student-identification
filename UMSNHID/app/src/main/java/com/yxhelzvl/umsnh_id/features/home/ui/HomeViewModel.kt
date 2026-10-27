package com.yxhelzvl.umsnh_id.features.home.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yxhelzvl.umsnh_id.core.data.SessionManager
import com.yxhelzvl.umsnh_id.features.home.network.HomeRepository
import com.yxhelzvl.umsnh_id.features.register.ui.RegisterEvent
import com.yxhelzvl.umsnh_id.features.register.ui.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay

class HomeViewModel(private val repository: HomeRepository, private val sessionManager: SessionManager) : ViewModel(){

    // Estados para controlar la carga y los datos
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()
    private val _expirationTime = mutableStateOf("")
    val expirationTime: State<String> = _expirationTime
    init {

    }
    init {
        loadExpirationTime()
    }

    private fun loadExpirationTime() {
        viewModelScope.launch {
            val expiration = sessionManager.getTokenExpiration()
            _expirationTime.value = sessionManager.formatExpirationTime(expiration)
        }
    }
    private fun loadStudentData(id: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {
                kotlinx.coroutines.delay(2000)
                val response = repository.EstudianteById(id)
                if (response.success) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        studentData = response.data?.usuario,
                        error = null
                    )
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = response.message ?: "Error desconocido"
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error de conexión"
                )
            }
        }
    }


    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.SetError -> {
                _state.update { it.copy(error = event.error) }
            }
            is HomeEvent.SetId_Estudiante -> {
                _state.update { it.copy(id_student = event.id_student) }
            }
            HomeEvent.LoadDataStudent -> {
                state.value.id_student?.let { loadStudentData(it) }
            }
            HomeEvent.ResetCerrarSesion -> {
                _state.update { it.copy(cerrarSesion = false) }
            }
            HomeEvent.CerrarSesion -> {
                _state.update { it.copy(cerrarSesion = true) }
            }
            else -> Unit
        }

    }

}

