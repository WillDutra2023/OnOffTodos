package br.com.william.onofftodos.domain.repositories

import br.com.william.onofftodos.core.UiState
import br.com.william.onofftodos.data.database.entities.TodoEntity
import br.com.william.onofftodos.domain.model.Todos
import kotlinx.coroutines.flow.Flow


interface TodosDBRepository {
    suspend fun update(todo: Todos): Flow<UiState<Unit>>
    suspend fun save(todo: Todos): Flow<UiState<Unit>>
    suspend fun saveAll(todo: List<Todos>): Flow<UiState<Unit>>
    suspend fun delete(id: Int): Flow<UiState<Unit>>
    suspend fun findAll(): Flow<UiState<List<TodoEntity>>>
    suspend fun findById(id: String): Flow<UiState<TodoEntity>>
}

