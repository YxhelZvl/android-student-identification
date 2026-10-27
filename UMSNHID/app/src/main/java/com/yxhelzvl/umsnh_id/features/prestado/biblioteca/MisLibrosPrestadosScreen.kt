package com.yxhelzvl.umsnh_id.features.prestado.biblioteca

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
// Data class para representar un libro (ya definida en la pantalla anterior)
// data class Libro(val titulo: String, val autor: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisLibrosPrestadosScreen() {
    // Simulación de la lista de libros prestados (esto vendría de tu base de datos)
    val librosPrestados = remember {
        mutableStateListOf(
            Libro("El Principito", "Antoine de Saint-Exupéry"),
            Libro("Harry Potter y la piedra filosofal", "J.K. Rowling"),
            Libro("Matar un ruiseñor", "Harper Lee")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Libros Prestados") }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                if (datosLibros.libros.isEmpty()) {
                    Text(
                        text = "No tienes ningún libro prestado actualmente.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(datosLibros.libros) { libro ->
                            LibroPrestadoItem(libro = libro)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun LibroPrestadoItem(libro: Libro1) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = libro.titulo, style = MaterialTheme.typography.titleMedium)
            Text(text = "Por AUTOR LIBRO", style = MaterialTheme.typography.bodySmall)
            // Aquí podrías agregar más información como la fecha de préstamo, fecha de devolución, etc.
        }
    }
}