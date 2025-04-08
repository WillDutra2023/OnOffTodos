package br.com.william.onofftodos.ui.contracts

import br.com.william.onofftodos.domain.model.Todos

data class TodoDetailContract(
    val todos: Todos? = null,
    val isLoading: Boolean = true
)
