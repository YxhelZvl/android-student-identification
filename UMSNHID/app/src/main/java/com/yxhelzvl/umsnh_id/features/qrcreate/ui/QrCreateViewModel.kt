package com.yxhelzvl.umsnh_id.features.qrcreate.ui

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.yxhelzvl.umsnh_id.core.navigation.QrCreate
import com.yxhelzvl.umsnh_id.features.home.network.HomeRepository
import com.yxhelzvl.umsnh_id.features.home.network.HomeService
import com.yxhelzvl.umsnh_id.features.qrcreate.data.remote.QrInfoApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


//Nota: esto es de prueba para ver que funciona luego repository ver donde meterlo
// ya que se ocupa varias veces en diferentes features ya que lo tengo en Home
//para hacerlo "Global"
class QrCreateViewModel(private val apiService: QrInfoApiService,
                        private val savedStateHandle: SavedStateHandle

) : ViewModel() {

    // Estados para controlar la carga y los datos
    private val _state = MutableStateFlow(QrCreateState())
    val state = _state.asStateFlow()

    init {
        val args = savedStateHandle.toRoute<QrCreate>()
        viewModelScope.launch {
            args.id_student.let { loadStudentData(it) }
        }




    }

    private fun loadStudentData(id: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {
                delay(2000)
                val response = apiService.QrInfoUsuario(id)
                if (response.success) {
                    _state.update {
                        it.copy(

                            studentData = response.data?.inscripcion,
                            error = null
                        )

                    }
                    _state.update { it.copy(isLoading = false,
                        qrBitmap = generarCodigoQr("" +
                            "Nombre: ${state.value.studentData?.nombreUsuario}\n" +
                            //"Matricula: ${state.value.studentData?.email}\n" +
                            "Email: ${state.value.studentData?.email}\n" +
                            "Ciclo: ${state.value.studentData?.cicloEscolar}\n" +
                            "Fecha_Inscripcion: ${state.value.studentData?.fechaInscripcion}\n" +
                            "Estado: ${state.value.studentData?.estado}")
                    ) }

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


    fun onEvent(event: QrCreateEvent) {
        when (event) {
            is QrCreateEvent.SetError -> {
                _state.update { it.copy(error = event.error) }
            }
            is QrCreateEvent.SetId_Estudiante -> {
                _state.update { it.copy(id_student = event.id_student) }
            }
            QrCreateEvent.LoadDataStudent -> {
                state.value.id_student?.let { loadStudentData(it) }
            }
            QrCreateEvent.GenerarQr -> {
                _state.update { it.copy(qrBitmap = generarCodigoQr("" +
                        "Nombre: ${state.value.studentData?.nombreUsuario}\n" +
                        //"Matricula: ${state.value.studentData?.email}\n" +
                        "Email: ${state.value.studentData?.email}\n" +
                        "Ciclo: ${state.value.studentData?.cicloEscolar}\n" +
                        "Fecha_Inscripcion: ${state.value.studentData?.fechaInscripcion}\n" +
                        "Estado: ${state.value.studentData?.estado}")
                ) }



            }
            else -> Unit
        }

    }


    fun generarCodigoQr(contenido: String): Bitmap {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(contenido, BarcodeFormat.QR_CODE,512,512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bmp = Bitmap.createBitmap(width,height, Bitmap.Config.RGB_565)

        for (x in 0 until width){
            for (y in 0 until height){
                bmp.setPixel(x,y,if (bitMatrix[x,y]) Color.Black.toArgb() else Color.White.toArgb())
            }
        }

        return bmp
    }




}