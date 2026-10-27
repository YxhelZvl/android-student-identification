package com.yxhelzvl.umsnh_id.features.register.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yxhelzvl.umsnh_id.features.register.data.remote.RegisterRepository
import com.yxhelzvl.umsnh_id.features.register.data.remote.model.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: RegisterRepository) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private var idCarrera by mutableStateOf(0)


    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.SetName -> {
                _state.update { it.copy(name = event.name) }
            }

            is RegisterEvent.SetPaternalSurname -> {
                _state.update { it.copy(paternalSurname = event.surname) }
            }

            is RegisterEvent.SetMaternalSurname -> {
                _state.update { it.copy(maternalSurname = event.surname) }
            }

            is RegisterEvent.SetMatricula -> {
                _state.update { it.copy(matricula = event.matricula) }
            }

            is RegisterEvent.SetEmail -> {
                _state.update { it.copy(email = event.email) }
            }

            is RegisterEvent.SetPassword -> {
                _state.update { it.copy(password = event.password) }
            }

            is RegisterEvent.SelectCareer -> {
                // Actualizamos la carrera seleccionada y cerramos el dropdown
                _state.update { it.copy(selectedCareer = event.career, isDropdownExpanded = false) }

                val carreraId = when (event.career){
                    "Ingeniería Eléctrica" -> carrerasToInt.Ingenieria_Electrica.id
                    "Ingeniería Electrónica" -> carrerasToInt.Ingenieria_Electronica.id
                    "Ingeniería en Computación" -> carrerasToInt.Ingenieria_en_Computacion.id
                    else -> 0
                }
                idCarrera = carreraId

            }

            RegisterEvent.TogglePasswordVisibility -> {
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }

            is RegisterEvent.ToggleDropdown -> {
                _state.update { it.copy(isDropdownExpanded = !it.isDropdownExpanded) }
            }
            RegisterEvent.ChangeDialogAlert -> {
                _state.update { it.copy(openDialog = !it.openDialog) }
            }

            RegisterEvent.Submit -> {
                registerEstudiante()
            }

            else -> Unit
        }
    }

    private fun registerEstudiante() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val request = RegisterRequest(
                    nombre = state.value.name,
                    apellidoP = state.value.paternalSurname,
                    apellidoM = state.value.maternalSurname,
                    matricula = state.value.matricula,
                    email = state.value.email,
                    contraseña = state.value.password,
                    idRol = 1,//Default rol Estudiante al registrarse
                    idCarrera = idCarrera
                )
                val response = repository.registerEstudiante(request)
                _state.update {  it.copy(msg = response.message)}
            }catch (e: Exception){
                //println("ERROR AL REGISTRAR")
                _state.update {  it.copy(msg = "ERROR AL REGISTRAR")}
            }finally {
                _state.update { it.copy(isLoading = false, openDialog = true) }
            }
        }
    }


    enum class carrerasToInt (val id: Int){
            Ingenieria_Electrica(1),
            Ingenieria_Electronica(2),
            Ingenieria_en_Computacion(3);
    }
}