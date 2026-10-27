package com.yxhelzvl.umsnh_id.features.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yxhelzvl.umsnh_id.core.data.SessionManager
import com.yxhelzvl.umsnh_id.core.utils.JwtUtils
import com.yxhelzvl.umsnh_id.features.login.data.models.LoginResponse
import com.yxhelzvl.umsnh_id.features.login.data.remote.AuthApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val apiService: AuthApiService,
    private val sessionManager: SessionManager
): ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onEvent(event: LoginEvent){
        when (event) {
            is LoginEvent.SetUsername -> {
                _state.update { it.copy(username = event.username) }
            }
            is LoginEvent.SetPassword -> {
                _state.update { it.copy(password = event.password) }
            }
            LoginEvent.TogglePasswordVisibility -> {
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }
            LoginEvent.ClearErrors -> {
                _state.update { it.copy(error = null) }
            }
            LoginEvent.ClearNavigateToHome -> {
                _state.update { it.copy(navigateToHome = false) }
            }
            LoginEvent.ClearIdUsuario -> {
                _state.update { it.copy(id_usuario = null) }
            }
            LoginEvent.Login -> {
                performLogin()
            }
            LoginEvent.LoginWithGoogle -> {
                // Lógica para autenticación con Google
            }
            LoginEvent.ChangeDialogAlert -> {
                _state.update { it.copy(openDialog = !it.openDialog) }
            }
            else -> Unit
        }
    }

    private fun performLogin() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            runCatching {
                apiService.login(
                    username = state.value.username,
                    password = state.value.password
                )
            }.fold(
                onSuccess = { response ->
                    if (response.success) {
                        response.data?.let { data ->
                            handleSuccessfulLogin(data)
                            _state.update { it.copy(navigateToHome = true, id_usuario = response.data.usuario.idUsuario.toString()) }
                        } ?: handleError("Datos de sesión inválidos")
                    } else {
                        handleError(response.message ?: "Error desconocido")
                    }

                },
                onFailure = { exception ->
                    handleError(exception.message ?: "Error de conexión")
                }
            )
        }
    }

    private suspend fun handleSuccessfulLogin(data: LoginResponse) {
        val token = data.token.ifBlank {
            handleError("Token vacío"); return
        }

        val expirationSeconds = JwtUtils.getExpiration(token) ?: run {
            handleError("Token sin expiración"); return
        }

        val userId = JwtUtils.getUserId(token) ?: run {
            handleError("ID de usuario no encontrado en token"); return
        }

        sessionManager.saveSession(
            token = token,
            userId = userId.toString(),
            expiration = expirationSeconds * 1000 // Convertir a milisegundos
        )

        // Navegar a home
        //_navigateToHome.send(Unit)
        _state.update { it.copy(isLoading = false) }
    }

    private fun handleError(errorMessage: String) {
        _state.update {
            it.copy(
                isLoading = false,
                error = errorMessage,
                openDialog = true
            )
        }
    }


}