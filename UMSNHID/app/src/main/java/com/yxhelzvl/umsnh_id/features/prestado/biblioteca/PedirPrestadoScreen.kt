package com.yxhelzvl.umsnh_id.features.prestado.biblioteca
import android.graphics.Bitmap
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.*
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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import java.util.Hashtable

// Data class para representar un libro
data class Libro(val titulo: String, val autor: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedirPrestadoScreen() {
    val context = LocalContext.current
    // Lista de libros disponibles (simulada)
    val listaLibrosDisponibles = remember {
        mutableStateListOf(
            Libro("Cien años de soledad", "Gabriel García Márquez"),
            Libro("El Señor de los Anillos", "J.R.R. Tolkien"),
            Libro("Orgullo y prejuicio", "Jane Austen"),
            Libro("Don Quijote de la Mancha", "Miguel de Cervantes"),
            Libro("1984", "George Orwell")
        )
    }

    // Lista de libros que el usuario quiere pedir prestados
    val librosParaPrestar = remember { mutableStateListOf<Libro>() }

    // Estado para controlar si mostrar la pantalla de confirmación QR
    var mostrarQrConfirmation by remember { mutableStateOf(false) }
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // ID de usuario simulado
    val idUsuario = "usuario123"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pedir Prestado un Libro") }
            )
        },
        content = { paddingValues ->
            if (!mostrarQrConfirmation) {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Libros Disponibles",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyColumn {
                        items(listaLibrosDisponibles) { libro ->
                            LibroItem(
                                libro = libro,
                                onAgregarClick = {
                                    if (!librosParaPrestar.contains(libro)) {
                                        librosParaPrestar.add(libro)
                                    }
                                }
                            )
                        }
                    }
                }
            } else {
                qrBitmap?.let { bitmap ->
                    QrConfirmationScreen(qrBitmap = bitmap, onClose = { mostrarQrConfirmation = false })
                }
            }
        },
        bottomBar = {
            if (!mostrarQrConfirmation && librosParaPrestar.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.padding(start =16.dp ,end = 16.dp, top = 16.dp, bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding())) {
                        Text(
                            text = "Libros a pedir prestado:",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        librosParaPrestar.forEach { libro ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "- ${libro.titulo}")
                                IconButton(onClick = { librosParaPrestar.remove(libro) }) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Eliminar libro")
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            mostrarQrConfirmation = true
                            val data = "$idUsuario:\n${librosParaPrestar.joinToString { it.titulo }}"
                            qrBitmap = generateQrCode("Libr@\n ${data}")
                        }, modifier = Modifier.fillMaxWidth()) {
                            Text("Confirmar Préstamo")
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun QrConfirmationScreen(qrBitmap: Bitmap, onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Escanea este código QR para confirmar el préstamo:", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            bitmap = qrBitmap.asImageBitmap(),
            contentDescription = "Código QR del préstamo",
            modifier = Modifier
                .fillMaxWidth(0.8f) // Ocupa el 80% del ancho
                .aspectRatio(1f) // Asegura que sea cuadrado
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onClose) {
            Text("Volver")
        }
    }
}

@Composable
fun LibroItem(libro: Libro, onAgregarClick: (Libro) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = libro.titulo, style = MaterialTheme.typography.titleMedium)
                Text(text = "Por ${libro.autor}", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { onAgregarClick(libro) }) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar libro")
                Spacer(modifier = Modifier.width(4.dp))
                Text("Agregar")
            }
        }
    }
}

fun generateQrCode(data: String): Bitmap? {
    val bitMatrix = try {
        val hints = Hashtable<EncodeHintType, String>()
        hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
        QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, 512, 512, hints)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }

    val width = bitMatrix.width
    val height = bitMatrix.height
    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
    for (x in 0 until width) {
        for (y in 0 until height) {
            bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.Black.toArgb() else Color.White.toArgb())
        }
    }
    return bmp
}