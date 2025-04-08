package br.com.william.onofftodos.domain.usecase.db

import br.com.william.onofftodos.core.UiState
import br.com.william.onofftodos.domain.model.Todos
import kotlinx.coroutines.flow.Flow

interface SaveTodosUseCase {
    suspend fun execute(todo:Todos): Flow<UiState<Unit>>
}