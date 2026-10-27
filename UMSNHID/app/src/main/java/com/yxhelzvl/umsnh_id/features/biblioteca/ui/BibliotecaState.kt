package com.yxhelzvl.umsnh_id.features.biblioteca.ui

data class BibliotecaState(
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val loans: List<Loan> = emptyList(),
    val searchQuery: String = "",
    val selectedSection: BibliotecaSection = BibliotecaSection.EXPLORE
)

enum class BibliotecaSection {
    EXPLORE, LOANS, HISTORY
}

// Data Classes temporales
data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val year: Int,
    val available: Boolean,
    val coverUrl: String
)

data class Loan(
    val id: Int,
    val bookId: Int,
    val loanDate: String,
    val dueDate: String,
    val status: LoanStatus
)

enum class LoanStatus { ACTIVE, OVERDUE, RETURNED }