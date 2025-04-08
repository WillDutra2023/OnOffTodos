package br.com.william.onofftodos.domain.repositories

import br.com.william.onofftodos.core.UiState
import br.com.william.onofftodos.data.model.TodosDTO
import br.com.william.onofftodos.domain.model.Todos
import kotlinx.coroutines.flow.Flow

interface TodosRespository {
    suspend fun getTodoList(): Flow<UiState<List<TodosDTO>>>
    suspend fun getTodoDetail(id: String): Flow<UiState<TodosDTO>>
    suspend fun patchTodo(todos: Todos): Flow<UiState<TodosDTO>>
    suspend fun postTodo(todos: Todos): Flow<UiState<TodosDTO>>
}