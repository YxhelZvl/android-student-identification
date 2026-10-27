package com.yxhelzvl.umsnh_id.features.qrcreate.ui

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.yxhelzvl.umsnh_id.features.home.ui.HomeEvent
import com.yxhelzvl.umsnh_id.features.home.ui.HomeState
import com.yxhelzvl.umsnh_id.features.home.ui.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun QrCreateScreenRoot(
    viewModel: QrCreateViewModel = koinViewModel(),
    navigateToBack : () -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

   // LaunchedEffect(Unit) {
        //viewModel.onEvent(QrCreateEvent.SetId_Estudiante(id_student = id_student))
        //viewModel.onEvent(QrCreateEvent.LoadDataStudent)
        //println("idQrCreate: ${id_student}")
   // }
    QrCreateScreen(state = state,
        onEvent = { event ->
            when (event) {
                //else -> Unit
                is QrCreateEvent.NavigateToBack -> navigateToBack()
                else -> viewModel.onEvent(event = event)
            }
            //viewModel.onEvent(event = event)

        })
}

@Composable
fun QrCreateScreen(
    state: QrCreateState,
    onEvent: (QrCreateEvent) -> Unit
){

    Scaffold(
    modifier = Modifier
        .fillMaxSize()
    ) { paddingValues ->

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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
            ){
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Color.Black,
                    modifier = Modifier.scale(1.5f).size(width = 60.dp, height = 30.dp).clickable {
                        onEvent(QrCreateEvent.NavigateToBack)
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


                /*Button(
                    modifier = Modifier
                        .padding(8.dp),
                    onClick = {
                        onEvent(QrCreateEvent.GenerarQr)
                    }
                ) {
                    Text(text = "Generar")
                }*/
                if(!state.isLoading){
                    state.qrBitmap?.let { bit ->

                        state.studentData?.let{
                            Image(
                                modifier = Modifier
                                    .size(250.dp),
                                bitmap = bit.asImageBitmap(),
                                contentDescription = "Codigo Qr"
                            )
                            Text(text = "Nombre: ${state.studentData.nombreUsuario}\n" +

                                    "Email: ${state.studentData.email}\n" +
                                    //"Ciclo: ${state.studentData.ciclo}\n" +
                                    "Fecha_Inscripcion: ${state.studentData.fechaInscripcion}\n" +
                                    "Estado: ${state.studentData.estado}\n"
                            )
                        }

                    }
                }else{
                    CircularProgressIndicator()
                }




            }
        }




    }






}




