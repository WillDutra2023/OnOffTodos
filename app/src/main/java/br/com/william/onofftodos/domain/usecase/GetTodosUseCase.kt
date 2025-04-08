package br.com.william.onofftodos.domain.usecase

import br.com.william.onofftodos.core.UiState
import br.com.william.onofftodos.domain.model.Todos
import kotlinx.coroutines.flow.Flow

interface GetTodosUseCase {
    suspend fun execute() : Flow<UiState<List<Todos>>>
}