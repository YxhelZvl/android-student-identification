package com.yxhelzvl.umsnh_id.features.register.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yxhelzvl.umsnh_id.core.components.PasswordTextField
import org.koin.androidx.compose.koinViewModel


@Composable
fun RegisterScreenRoot(
    viewModel: RegisterViewModel = koinViewModel(),
    navigateToLogin: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    //val state by viewModel.state.collectAsState()

    RegisterScreen(state = state, onEvent = { event ->
        when (event) {
            is RegisterEvent.NavigateToLogin -> navigateToLogin()
            //else -> Unit
            else -> viewModel.onEvent(event = event)
        }
        //viewModel.onEvent(event = event)

    })

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    state: RegisterState, onEvent: (RegisterEvent) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6A1B9A), Color(0xFFD81B60)
                    )
                )
            )
    ) {
        if (state.openDialog){
            AlertDialog(
                onDismissRequest = {
                   // onEvent(RegisterEvent.ChangeDialogAlert)
                },
                title = {
                    Text(text = "Registro")
                },
                text = {
                    Text(
                        text = state.msg ?: "Error"
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onEvent(RegisterEvent.ChangeDialogAlert)
                        }
                    ) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    /*TextButton(
                        onClick = {
                            onEvent(RegisterEvent.ChangeDialogAlert)
                        }
                    ) {
                        Text("Salir")
                    }*/
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.AppRegistration,
                contentDescription = "Registro",
                tint = Color.White,
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Crear Cuenta",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(48.dp))

            Card(
                modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Campo Nombre
                    OutlinedTextField(value = state.name,
                        onValueChange = { onEvent(RegisterEvent.SetName(it)) },
                        label = { Text("Nombre(s)") },
                        leadingIcon = { Icon(Icons.Default.Person, null) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )

                    // Campos Apellidos
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = state.paternalSurname,
                            onValueChange = { onEvent(RegisterEvent.SetPaternalSurname(it)) },
                            label = { Text("Apellido Paterno") },
                            modifier = Modifier.weight(1f),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            )
                        )

                        OutlinedTextField(
                            value = state.maternalSurname,
                            onValueChange = { onEvent(RegisterEvent.SetMaternalSurname(it)) },
                            label = { Text("Apellido Materno") },
                            modifier = Modifier.weight(1f),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            )
                        )
                    }

                    // Campo Matrícula
                    OutlinedTextField(
                        value = state.matricula,
                        onValueChange = { onEvent(RegisterEvent.SetMatricula(it)) },
                        label = { Text("Matrícula") },
                        leadingIcon = { Icon(Icons.Default.Numbers, null) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )

                    // Campo Email
                    OutlinedTextField(
                        value = state.email,
                        onValueChange = { onEvent(RegisterEvent.SetEmail(it)) },
                        label = { Text("Correo electrónico") },
                        leadingIcon = { Icon(Icons.Default.Email, null) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )

                    // Campo Contraseña
                    PasswordTextField(value = state.password,
                        onValueChange = { onEvent(RegisterEvent.SetPassword(it)) },
                        isPasswordVisible = state.isPasswordVisible,
                        onToggleVisibility = { onEvent(RegisterEvent.TogglePasswordVisibility) })

                    //Menu deplegable carreras

                    OutlinedTextField(value = state.selectedCareer,
                        onValueChange = {},
                        label = { Text("Carrera") },
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        ),
                        trailingIcon = {
                            Icon(imageVector = if (state.isDropdownExpanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                                contentDescription = null,
                                modifier = Modifier.clickable { onEvent(RegisterEvent.ToggleDropdown) })
                        })


                    Box(
                        modifier = Modifier.padding(1.dp)
                    ) {
                        DropdownMenu(expanded = state.isDropdownExpanded,
                            onDismissRequest = { onEvent(RegisterEvent.ToggleDropdown) }) {
                            state.careerOptions.forEach { career ->

                                DropdownMenuItem(text = { Text(career) }, onClick = {
                                    onEvent(RegisterEvent.SelectCareer(career))
                                })

                            }
                        }
                    }


                    // Botón de Registro
                    Button(
                        onClick = { onEvent(RegisterEvent.Submit) },
                        enabled = isFormValid(state),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(color = Color.White)
                        } else {
                            Text("Registrarse")
                        }
                    }

                    // Enlace a Login
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text("¿Ya tienes cuenta? ", color = Color.White)
                        TextButton(onClick = { onEvent(RegisterEvent.NavigateToLogin) }) {
                            Text(
                                text = "Iniciar Sesión",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

// Función de validación (se puede mejorar)
private fun isFormValid(state: RegisterState): Boolean {
    return state.name.isNotBlank() &&
            state.paternalSurname.isNotBlank() &&
            state.matricula.isNotBlank() &&
            state.email.isNotBlank() &&
            state.password.length >= 6 &&
            state.selectedCareer.isNotBlank()
}