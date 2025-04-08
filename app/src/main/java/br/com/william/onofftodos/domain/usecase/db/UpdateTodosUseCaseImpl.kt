package br.com.william.onofftodos.domain.usecase.db

import android.util.Log
import br.com.william.onofftodos.core.UiState
import br.com.william.onofftodos.domain.model.Todos
import br.com.william.onofftodos.domain.repositories.TodosDBRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateTodosUseCaseImpl(private val todosDBRepository: TodosDBRepository) :
    UpdateTodosUseCase {
    override suspend fun execute(todos: Todos): Flow<UiState<Unit>> = flow {
        todosDBRepository.update(todos).collect { todoResponse ->
            when(todoResponse){
                is UiState.Success<*> -> {
                    Log.d("UpdateTodosUseCaseImpl", "SUCCESS")
                    emit(UiState.Success(data = null))
                }
                is UiState.Error<*> -> {
                    emit(UiState.Error(message = todoResponse.message))
                }
                else -> {
                    emit(UiState.Loading())
                }
            }
        }
    }

}