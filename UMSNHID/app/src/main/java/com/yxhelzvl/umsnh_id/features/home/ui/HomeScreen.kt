package com.yxhelzvl.umsnh_id.features.home.ui


import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yxhelzvl.umsnh_id.R
import com.yxhelzvl.umsnh_id.core.data.SessionManager
import com.yxhelzvl.umsnh_id.features.home.network.model.Data
import com.yxhelzvl.umsnh_id.features.login.ui.LoginScreen
import com.yxhelzvl.umsnh_id.features.register.ui.RegisterEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel(),
    id_student: String,
    navigateToGenerarQr : (String) -> Unit,
    navigateToEscanearQr : () -> Unit,
    navigateToLogin : () -> Unit,
    navigateToBiblioteca : () -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    val expirationTime by viewModel.expirationTime
    LaunchedEffect(Unit) {
        viewModel.onEvent(HomeEvent.SetId_Estudiante(id_student = id_student))
        viewModel.onEvent(HomeEvent.LoadDataStudent)
    }

    HomeScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is HomeEvent.NavigateToGenerarQr -> navigateToGenerarQr(id_student)
                is HomeEvent.NavigateToEscanearQr -> navigateToEscanearQr()
                is HomeEvent.NavigateToLogin -> navigateToLogin()
                is HomeEvent.NavigateToBiblioteca -> navigateToBiblioteca()
                //else -> Unit
                else -> viewModel.onEvent(event = event)
            }
            //viewModel.onEvent(event = event)

        })

    /*
    Column(modifier = Modifier.padding(30.dp)) {
        Text(
            text = "Token expira el: $expirationTime",
            style = MaterialTheme.typography.bodyMedium
        )
        // ...
    }
    */

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    userInfo: UserInfo = UserInfo(), // Suponiendo una data class UserInfo
    onEvent: (HomeEvent) -> Unit
) {
    val sessionManager: SessionManager = koinInject()
    var showAccountMenu by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6A1B9A),
                        Color(0xFFD81B60)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // TopBar
            TopAppBar(
                title = { },
                actions = {
                    IconButton(onClick = { showAccountMenu = true }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Mi Cuenta",
                            tint = Color.White
                        )
                    }
                    DropdownMenu (
                        expanded = showAccountMenu,
                        onDismissRequest = { showAccountMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Cerrar Sesión") },
                            onClick = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    sessionManager.logout()
                                }
                                onEvent(HomeEvent.CerrarSesion)
                                showAccountMenu = false
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    actionIconContentColor = Color.White
                )
            )
            LaunchedEffect(state.cerrarSesion) {
                if(state.cerrarSesion){
                    onEvent(HomeEvent.ResetCerrarSesion)
                    onEvent(HomeEvent.NavigateToLogin)

                }
            }

            state.error?.let {
                AlertDialog(
                    onDismissRequest = {
                        // onEvent(RegisterEvent.ChangeDialogAlert)
                    },
                    title = {
                        Text(text = "Datos")
                    },
                    text = {
                        Text(
                            text = state.error ?: "Error"
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                onEvent(HomeEvent.SetError(null))
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

            // Card de Información
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Información",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        IconButton(
                            onClick = { onEvent(HomeEvent.NavigateToGenerarQr) },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.QrCode2,
                                contentDescription = "ID Digital",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(16.dp))

                    // Datos del usuario
                    if(state.isLoading){
                        ShimmerEffect(repeatCount = 1) { brush ->
                            InfoRowLoad("Nombre:", brush)
                            InfoRowLoad("Matrícula:", brush)
                            InfoRowLoad("Correo:", brush)
                            //InfoRowLoad("Ciclo Escolar:", brush)
                        }
                    }else{
                        state.studentData?.let {
                            InfoRow("Nombre:", it.nombre)
                            InfoRow("Matrícula:", it.matricula)
                            InfoRow("Correo:", it.email)
                            //InfoRow("Ciclo Escolar:", it.ciclo)
                        }
                    }


                }
            }

            // Botones de acción
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                ExtendedFloatingActionButton(
                    onClick = {onEvent(HomeEvent.NavigateToEscanearQr)},
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Escanear QR"
                        )
                        Text("Escanear QR")
                    }
                }

                ExtendedFloatingActionButton(
                    onClick = { onEvent(HomeEvent.NavigateToBiblioteca)},
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Book,
                            contentDescription = "Biblioteca"
                        )
                        Text("Biblioteca")
                    }
                }

                ExtendedFloatingActionButton(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Computer,
                            contentDescription = "Laboratorio"
                        )
                        Text("Laboratorio")
                    }
                }
            }




        }
    }

}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }

}

@Composable
fun InfoRowLoad(label: String, brush: Brush) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
                .height(20.dp) // Misma altura que tu botón real
                .background(
                    brush = brush,
                    shape = RoundedCornerShape(8.dp) // Misma forma que tus botones
                )

        )
    }

}

// Data class de ejemplo
data class UserInfo(
    val nombreCompleto: String = "Estudiante de ejemplo",
    val matricula: String = "0000000",
    val email: String = "estudiante@example.com",
    val cicloEscolar: String = "2025/2025"
)
@Composable
fun ShimmerEffect(
    repeatCount: Int = 1,
    content: @Composable (brush: Brush) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = alpha),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = alpha)
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(0f, 0f),
        end = Offset(200f, 200f)
    )

    Column {
        repeat(repeatCount) {
            content(brush)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun FloatingLoader() {
    val infiniteTransition = rememberInfiniteTransition()
    val verticalOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 20f, // Rango de movimiento (ajustable)
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_google_logo),
            contentDescription = "Cargando",
            modifier = Modifier
                .size(120.dp)
                .offset(y = verticalOffset.dp)
                .graphicsLayer {
                    alpha = 0.8f // Opcional: efecto de transparencia
                }
        )
    }
}