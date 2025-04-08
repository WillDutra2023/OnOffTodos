package br.com.william.onofftodos.data.model

data class TodosDTO(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)