package br.com.william.onofftodos.domain.usecase

import br.com.william.onofftodos.core.ToTodoList
import br.com.william.onofftodos.core.UiState
import br.com.william.onofftodos.domain.model.Todos
import br.com.william.onofftodos.domain.repositories.TodosRespository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTodoDetailUseCaseImpl(private val todosRespository: TodosRespository) :
    GetTodoDetailUseCase {
    override suspend fun execute(id: String): Flow<UiState<Todos>> = flow {
        todosRespository.getTodoDetail(id).collect { todoResponse ->
            when(todoResponse){
                is UiState.Success<*> -> {
                    val todoList = todoResponse.data?.ToTodoList()
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