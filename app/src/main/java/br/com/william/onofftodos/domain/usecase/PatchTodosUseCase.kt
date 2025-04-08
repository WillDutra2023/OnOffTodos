package br.com.william.onofftodos.domain.usecase

import br.com.william.onofftodos.core.UiState
import br.com.william.onofftodos.domain.model.Todos
import kotlinx.coroutines.flow.Flow

interface PatchTodosUseCase {
    suspend fun execute(todo: Todos) : Flow<UiState<Todos>>
}