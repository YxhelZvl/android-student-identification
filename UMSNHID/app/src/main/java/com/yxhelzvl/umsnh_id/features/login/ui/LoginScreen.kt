package com.yxhelzvl.umsnh_id.features.login.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yxhelzvl.umsnh_id.R
import com.yxhelzvl.umsnh_id.core.components.PasswordTextField
import com.yxhelzvl.umsnh_id.core.data.SessionManager
import com.yxhelzvl.umsnh_id.features.register.ui.RegisterEvent
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = koinViewModel(),
    navigateToHome: (String) -> Unit,
    navigateToRegister: () -> Unit

){
    val state by viewModel.state.collectAsStateWithLifecycle()


    LoginScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is LoginEvent.NavigateToHome -> navigateToHome(state.id_usuario ?: "")
                is LoginEvent.NavigateToRegister -> navigateToRegister()
                //else -> Unit
                else -> viewModel.onEvent(event = event)
            }
            //viewModel.onEvent(event = event)
        }
    )


}

@Composable
fun LoginScreen(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit
) {

    val context = LocalContext.current
    state.error?.let { error ->
        Text(error)
        onEvent(LoginEvent.ClearErrors)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6A1B9A),  // Morado
                        Color(0xFFD81B60)   // Rosa
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo y título


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
                            text = state.error ?: "Usuario/Conraeña incorrectos"
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                onEvent(LoginEvent.ChangeDialogAlert)
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

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Logo",
                tint = Color.White,
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Bienvenido",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )

            Text(
                text = "Inicia sesión para continuar",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Formulario en tarjeta
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Campo de usuario
                    OutlinedTextField(
                        value = state.username,
                        onValueChange = { onEvent(LoginEvent.SetUsername(it)) },
                        label = { Text("Matricula o correo") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )

                    // Campo de contraseña
                    PasswordTextField(
                        value = state.password,
                        onValueChange = { onEvent(LoginEvent.SetPassword(it)) },
                        isPasswordVisible = state.isPasswordVisible,
                        onToggleVisibility = { onEvent(LoginEvent.TogglePasswordVisibility) }
                    )

                    // Botón de olvidó contraseña
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { Toast.makeText(context, "Olvide mi contraseña", Toast.LENGTH_SHORT).show() }) {
                            Text(
                                text = "¿Olvidaste tu contraseña?",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    // Botón de inicio de sesión
                    Button(
                        onClick = { onEvent(LoginEvent.Login)},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = MaterialTheme.shapes.large,
                        enabled = !state.isLoading && state.username.isNotBlank() && state.password.isNotBlank()
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(color = Color.White)
                        } else {
                            Text("Iniciar Sesión", style = MaterialTheme.typography.labelLarge)
                        }
                    }

                    LaunchedEffect(state.navigateToHome) {
                        if(state.navigateToHome){
                            onEvent(LoginEvent.ClearNavigateToHome)
                            onEvent(LoginEvent.NavigateToHome)
                        }

                    }

                    // Separador
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Divisor Horizontal (Reemplazo directo de Divider)
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 16.dp),
                            color = Color.Gray.copy(alpha = 0.3f),
                            thickness = 1.dp
                        )
                        Text(
                            text = "o",
                            modifier = Modifier.padding(horizontal = 8.dp),
                            color = Color.Gray
                        )
                        // Divisor Vertical (Nuevo componente)
                        VerticalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.Blue,
                            thickness = 2.dp
                        )
                    }

                    // Botón de Google
                    OutlinedButton(
                        onClick = { onEvent(LoginEvent.NavigateToHome) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = MaterialTheme.shapes.large,
                        enabled = false
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_google_logo), // Añade tu icono de Google
                                contentDescription = "Google Logo",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Continuar con Google", style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
            }

            // Registrarse
            Row(
                modifier = Modifier.padding(top = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("¿No tienes cuenta? ", color = Color.White)
                TextButton(onClick = { onEvent(LoginEvent.NavigateToRegister)  }) {
                    Text(
                        text = "Regístrate",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }





    }


}
