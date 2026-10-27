package com.yxhelzvl.umsnh_id.core.navigation

import kotlinx.serialization.Serializable


@Serializable
object Login

@Serializable
object Register

@Serializable
data class Home(val id_student:String)

@Serializable
object QrScanner

@Serializable
data class QrCreate (val id_student: String)

@Serializable
object Biblioteca

@Serializable
object PrestadoB

@Serializable
object MisLibrosPrestados