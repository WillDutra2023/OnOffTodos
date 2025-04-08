package br.com.william.onofftodos.domain.model

data class Todos(
    val completed: Boolean? = null,
    val id: Int? = null,
    val title: String? = "",
    val userId: Int? = null
)