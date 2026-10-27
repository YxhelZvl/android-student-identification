package com.yxhelzvl.umsnh_id.features.biblioteca.ui

sealed class BibliotecaEvent {
    object LoadBooks : BibliotecaEvent()
    object LoadLoans : BibliotecaEvent()
    data class SearchBooks(val query: String) : BibliotecaEvent()
    data class SelectBook(val bookId: Int) : BibliotecaEvent()
}