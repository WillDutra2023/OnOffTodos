package br.com.william.onofftodos.domain.usecase.db

import android.util.Log
import br.com.william.onofftodos.core.ToTodoList
import br.com.william.onofftodos.core.UiState
import br.com.william.onofftodos.core.toTodo
import br.com.william.onofftodos.domain.model.Todos
import br.com.william.onofftodos.domain.repositories.TodosDBRepository
import br.com.william.onofftodos.domain.usecase.GetTodoDetailUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FindByIdTodosUseCaseImpl(private val todosDBRepository: TodosDBRepository) :
    FindByIdTodosUseCase {
    override suspend fun execute(id: String): Flow<UiState<Todos>> = flow {
        todosDBRepository.findById(id).collect { todoResponse ->
            when(todoResponse){
                is UiState.Success<*> -> {
                    val todoList = todoResponse.data?.toTodo()
                    Log.d("FindByIdTodosUseCaseImpl",todoList.toString() )
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