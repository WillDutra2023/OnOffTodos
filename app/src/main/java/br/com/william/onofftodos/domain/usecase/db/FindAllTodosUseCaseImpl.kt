package br.com.william.onofftodos.domain.usecase.db

import android.util.Log
import br.com.william.onofftodos.core.UiState
import br.com.william.onofftodos.core.toListTodo
import br.com.william.onofftodos.domain.model.Todos
import br.com.william.onofftodos.domain.repositories.TodosDBRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FindAllTodosUseCaseImpl(private val todosDBRepository: TodosDBRepository) :
    FindAllTodosUseCase {
    override suspend fun execute(): Flow<UiState<List<Todos>>> = flow {
        todosDBRepository.findAll().collect { todoResponse ->
            when (todoResponse) {
                is UiState.Success<*> -> {
                    Log.d("FindAllTodosUseCaseImpl", todoResponse.data.toString())
                    val todoList = todoResponse.data?.toListTodo()
                    emit(UiState.Success(data = todoList))
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