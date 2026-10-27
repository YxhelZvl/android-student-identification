package com.yxhelzvl.umsnh_id.features.home.models

import java.time.LocalDate

data class Prestamo(
    val id_prestamo: Int,
    val id_usuario: Int,
    val id_ejemplar: Int,
    val fecha_prestamo: LocalDate,
    val fecha_devolucion_esperada: LocalDate,
    val fecha_devolucion_real: LocalDate?,
    val estado: EstadoPrestamo,
    val ejemplar: EjemplarDetalle? = null // Datos relacionados para UI
)

// Estado del préstamo (enum correspondiente al enum SQL)
enum class EstadoPrestamo {
    ACTIVO,
    COMPLETADO,
    RETRASADO
}

// Modelo detallado para UI (combina datos de Ejemplares + Catalogo)
data class EjemplarDetalle(
    val id_ejemplar: Int,
    val codigo_inventario: String,
    val nombre: String,
    val tipo: TipoEjemplar,
    val ubicacion: UbicacionEjemplar,
    val imagenUrl: String? = null
)

// Enums auxiliares
enum class TipoEjemplar { HERRAMIENTA, LIBRO, EQUIPO }
enum class UbicacionEjemplar { LABORATORIO, BIBLIOTECA }