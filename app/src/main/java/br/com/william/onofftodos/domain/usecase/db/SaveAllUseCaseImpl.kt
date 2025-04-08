package br.com.william.onofftodos.domain.usecase.db

import android.util.Log
import br.com.william.onofftodos.core.UiState
import br.com.william.onofftodos.domain.model.Todos
import br.com.william.onofftodos.domain.repositories.TodosDBRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveAllTodosUseCaseImpl(private val todosDBRepository: TodosDBRepository) :
    SaveAllUseCase {
    override suspend fun execute(todos: List<Todos>): Flow<UiState<Unit>> = flow {
        todosDBRepository.saveAll(todos).collect { todoResponse ->
            when(todoResponse){
                is UiState.Success<*> -> {
                    Log.d("SaveTodosUseCaseImpl", "SUCCESS")
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