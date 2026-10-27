package com.yxhelzvl.umsnh_id.features.home.ui

import com.yxhelzvl.umsnh_id.features.home.models.Prestamo
import com.yxhelzvl.umsnh_id.features.home.network.model.Data
import com.yxhelzvl.umsnh_id.features.home.network.model.Usuario

// Actualización del HomeState
data class HomeState(
    val isLoading: Boolean = false,
    val studentData: Usuario? = null,
    val id_student: String? = null,
    val error: String? = null,
    val cerrarSesion: Boolean = false,
    val activeLoans: List<Prestamo> = emptyList(), // Nuevo
    val isAdmin: Boolean = false // Nuevo para controlar rol
)
// Modelo de datos (ajusta según tu API)
data class StudentData(
    val nombre: String,
    val apellidoP: String,
    val apellidoM: String,
    val matricula: String,
    val email: String,
    val carrera: String,
    val facultad: String,
    val ciclo: String,
    val fecha_inicio: String,
    val fecha_final: String,
    val fecha_inscripcion: String,
    val estado: String,
)