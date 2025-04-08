package br.com.william.onofftodos.domain.usecase.db

import br.com.william.onofftodos.core.UiState
import br.com.william.onofftodos.domain.model.Todos
import kotlinx.coroutines.flow.Flow

interface FindByIdTodosUseCase {
    suspend fun execute(id: String): Flow<UiState<Todos>>
}