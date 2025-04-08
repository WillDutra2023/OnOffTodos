package br.com.william.onofftodos.ui.contracts

import br.com.william.onofftodos.domain.model.Todos

data class TodosContract(
    val todos: List<Todos> = listOf(),
    val isLoading: Boolean = false
)
