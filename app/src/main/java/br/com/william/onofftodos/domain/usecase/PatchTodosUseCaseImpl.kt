package br.com.william.onofftodos.domain.usecase

import br.com.william.onofftodos.core.ToTodoList
import br.com.william.onofftodos.core.UiState
import br.com.william.onofftodos.domain.model.Todos
import br.com.william.onofftodos.domain.repositories.TodosRespository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PatchTodosUseCaseImpl(private val todosRespository: TodosRespository): PatchTodosUseCase {
    override suspend fun execute(todo: Todos): Flow<UiState<Todos>> = flow {
        todosRespository.patchTodo(todo).collect { todoResponse ->
            when(todoResponse){
                is UiState.Success<*> -> {
                    emit(UiState.Success(data = todoResponse.data!!.ToTodoList()))
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