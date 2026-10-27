package com.yxhelzvl.umsnh_id.features.qrscanner.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.shouldShowRationale
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.yxhelzvl.umsnh_id.R
import com.yxhelzvl.umsnh_id.features.prestado.biblioteca.Libro1
import com.yxhelzvl.umsnh_id.features.prestado.biblioteca.datosLibros

@Composable
fun QrScannerScreenRoot(

){

    Scaffold(){paddingValues ->
        QrScannerScreen2(Modifier.padding(paddingValues))
    }


}







@OptIn(ExperimentalPermissionsApi::class) // Opt-in to use experimental permissions API
@Composable
fun QrScannerScreen(modifier: Modifier = Modifier) {

    // State to hold the scanned barcode value, saved across recompositions
    var barcode by rememberSaveable { mutableStateOf<String?>("No Code Scanned") }

    // State to manage the camera permission
    val permissionState = rememberPermissionState(
        Manifest.permission.CAMERA // Permission being requested
    )

    // State to track whether to show the rationale dialog for the permission
    var oncancel by remember(permissionState.status.shouldShowRationale) {
        mutableStateOf(permissionState.status.shouldShowRationale)
    }

    // Check if a barcode has been scanned
    if (barcode != null) {

        // Box to hold the UI elements and center them on the screen
        Box(
            modifier = Modifier
                .fillMaxSize() // Make the Box take up the entire screen
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF6A1B9A),
                            Color(0xFFD81B60)
                        )
                    )
                )
        ) {
            // Column to arrange UI elements vertically and center them
            Column(
                modifier = Modifier
                    .align(Alignment.Center) // Center the Column within the Box
                    .padding(16.dp), // Optional: Add padding
                horizontalAlignment = Alignment.CenterHorizontally, // Center children horizontally
                verticalArrangement = Arrangement.Center // Center children vertically
            ) {
                // Show rationale dialog if permission is denied and rationale can be shown
                if (oncancel) {
                    ShowRationaleDialog(
                        onDismiss = { oncancel = false }, // Callback when dialog is dismissed
                        onConfirm = { permissionState.launchPermissionRequest() }, // Callback to request permission
                        body = permissionState.permission // Permission being requested
                    )
                }

                // Determine the text to show based on the permission state
                val textToShow = if (permissionState.status.shouldShowRationale) {
                    // If the user has denied the permission but the rationale can be shown,
                    // explain why the app requires this permission
                    "The Camera permission is important for this app. Please grant the permission."
                } else if (!permissionState.status.isGranted) {
                    // If it's the first time the user lands on this feature, or the user
                    // doesn't want to be asked again for this permission, explain that the
                    // permission is required
                    "Camera permission required for this feature to be available. " +
                            "Please grant the permission"
                } else {
                    // If permission is granted, show the scanned barcode or a default message
                    barcode ?: "No Scanned"
                }



                // Show a button to scan a QR or barcode if permission is granted
                if (permissionState.status.isGranted) {
                    Button(onClick = { barcode = null }) {
                        Text("Scan QR or Barcode")
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(modifier = Modifier.fillMaxWidth(),
                        text = "Resultado: ")
                    Spacer(modifier = Modifier.height(3.dp))
                    // Display the determined text
                    Text(modifier = Modifier.fillMaxWidth(),
                        text= textToShow)

                } else {
                    // Show a button to request camera permission if not granted
                    Button(onClick = { permissionState.launchPermissionRequest() }) {
                        Text("Request permission")
                    }
                }
            }
        }
    } else {
        // If no barcode has been scanned, show the QR/barcode scanner
        ScanCode(onQrCodeDetected = {
            barcode = it // Update the barcode state with the scanned value
        })
    }

}



@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QrScannerScreen2(modifier: Modifier = Modifier) {
    // State para guardar el barcode detectado
    var barcode by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    // Estado de permisos para la cámara
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)


    // Función para verificar si es una URL válida
    fun isValidUrl(url: String): Boolean {
        return try {
            val uri = Uri.parse(url)
            uri.scheme?.let { it == "http" || it == "https" } ?: false
        } catch (e: Exception) {
            false
        }
    }


    // Box principal donde todo el contenido se coloca
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Fondo negro para la cámara
    ) {
        // Composable para el escaneo de códigos QR
        ScanCode(onQrCodeDetected = {
            // Actualizamos el valor de barcode cada vez que se detecta un nuevo código
            barcode = it
        })

        // Mostrar el código QR detectado en tiempo real, si existe
        barcode?.let { code ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.White.copy(alpha = 0.8f)) // Fondo semi-transparente
                    .padding(16.dp)
                    .navigationBarsPadding()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Código Detectado:",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = code, // El texto se actualiza en tiempo real
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )

                    if(code.contains("Libr@")){
                        Button(onClick = {

                            obtenerLibrosDesdeTexto(code).map { datosLibros.libros.add(it) }

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Añade tu icono
                                contentDescription = "Capturar",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("capturar")

                        }
                    }
                    // Botón solo para URLs válidas
                    if (isValidUrl(code)) {
                        var showDialog by remember { mutableStateOf(false) }

                        if (showDialog) {
                            AlertDialog(
                                onDismissRequest = { showDialog = false },
                                title = { Text("Abrir enlace externo") },
                                text = { Text("¿Estás seguro de abrir este enlace?\n$code") },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            showDialog = false
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(code))
                                            context.startActivity(intent)
                                        }
                                    ) { Text("Abrir") }
                                },
                                dismissButton = {
                                    TextButton(
                                        onClick = { showDialog = false }
                                    ) { Text("Cancelar") }
                                }
                            )
                        }

                        Button(onClick = { showDialog = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Añade tu icono
                                contentDescription = "Abrir",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Abrir en navegador")

                        }
                    }

                }
            }
        }

        // Si el permiso de cámara no ha sido concedido
        if (!permissionState.status.isGranted) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Se necesita permiso de cámara para escanear códigos QR.",
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { permissionState.launchPermissionRequest() }) {
                    Text("Conceder permiso")
                }
            }
        }
    }
}
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PermissionCamera(){
    // Camera permission state
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )
    if (cameraPermissionState.status.isGranted){
        Text(text = "Camera permission Granted")
    }else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                // If the user has denied the permission but the rationale can be shown,
                // then gently explain why the app requires this permission
                "The camera is important for this app. Please grant the permission."
            } else {
                // If it's the first time the user lands on this feature, or the user
                // doesn't want to be asked again for this permission, explain that the
                // permission is required
                "Camera permission required for this feature to be available. " +
                        "Please grant the permission"
            }
            Text(textToShow)
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text("Request permission")
            }
        }
    }
}



// Función para extraer títulos y convertirlos en una lista de objetos Libro
fun obtenerLibrosDesdeTexto(texto: String): List<Libro1> {
    // Extraer la parte del texto que contiene los títulos
    val titulosTexto = texto.substringAfter(":").trim()

    // Dividir los títulos por coma y limpiar los espacios
    val titulos = titulosTexto.split(",").map { it.trim() }

    // Convertir los títulos en objetos Libro
    return titulos.map { Libro1(it) }
}