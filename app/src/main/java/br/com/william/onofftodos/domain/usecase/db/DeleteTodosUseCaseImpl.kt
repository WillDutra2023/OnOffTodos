package br.com.william.onofftodos.domain.usecase.db

import br.com.william.onofftodos.core.ToTodoList
import br.com.william.onofftodos.core.UiState
import br.com.william.onofftodos.domain.model.Todos
import br.com.william.onofftodos.domain.repositories.TodosDBRepository
import br.com.william.onofftodos.domain.usecase.GetTodoDetailUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteTodosUseCaseImpl(private val todosDBRepository: TodosDBRepository) :
    DeleteTodosUseCase {
    override suspend fun execute(id: Int): Flow<UiState<Unit>> = flow {
        todosDBRepository.delete(id).collect { todoResponse ->
            when(todoResponse){
                is UiState.Success<*> -> {
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