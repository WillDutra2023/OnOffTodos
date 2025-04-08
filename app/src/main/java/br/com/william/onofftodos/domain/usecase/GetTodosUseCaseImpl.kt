package br.com.william.onofftodos.domain.usecase

import br.com.william.onofftodos.core.ToTodoList
import br.com.william.onofftodos.core.UiState
import br.com.william.onofftodos.domain.model.Todos
import br.com.william.onofftodos.domain.repositories.TodosRespository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTodosUseCaseImpl(private val todosRespository: TodosRespository): GetTodosUseCase {
    override suspend fun execute(): Flow<UiState<List<Todos>>> = flow {
        todosRespository.getTodoList().collect { todoResponse ->
            when(todoResponse){
                is UiState.Success<*> -> {
                    val todoList = todoResponse.data?.map { todos ->
                        todos.ToTodoList()
                    }
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