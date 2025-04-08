package br.com.william.onofftodos.data.repositories

import br.com.william.onofftodos.core.UiState
import br.com.william.onofftodos.data.model.TodosDTO
import br.com.william.onofftodos.data.services.todos.TodosApiServiceHelper
import br.com.william.onofftodos.domain.model.Todos
import br.com.william.onofftodos.domain.repositories.TodosRespository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

class TodosImpl(
    private val todosApiServiceHelper: TodosApiServiceHelper
) : TodosRespository, KoinComponent {
    override suspend fun getTodoList() = flow<UiState<List<TodosDTO>>> {
        emit(UiState.Loading())
        with(todosApiServiceHelper.getAllTodosListAPI()) {
            if (isSuccessful) {
                emit(UiState.Success(data = this.body()))
            } else {
                emit(UiState.Error(message = this.message()))
            }
        }
    }.catch {
        emit(UiState.Error(message = it.localizedMessage))
    }

    override suspend fun getTodoDetail(id: String) = flow<UiState<TodosDTO>> {
        emit(UiState.Loading())
        with(todosApiServiceHelper.getTodosDetailAPI(id)) {
            if (isSuccessful) {
                emit(UiState.Success(data = this.body()))
            } else {
                emit(UiState.Error(message = this.message()))
            }
        }
    }.catch {
        emit(UiState.Error(message = it.localizedMessage))
    }

    override suspend fun patchTodo(todos: Todos): Flow<UiState<TodosDTO>> =
        flow<UiState<TodosDTO>> {
            emit(UiState.Loading())
            with(todosApiServiceHelper.patchTodo(todos)) {
                if (isSuccessful) {
                    emit(UiState.Success(data = this.body()))
                } else {
                    emit(UiState.Error(message = this.message()))
                }
            }
        }.catch {
            emit(UiState.Error(message = it.localizedMessage))
        }

    override suspend fun postTodo(todos: Todos): Flow<UiState<TodosDTO>> =
        flow<UiState<TodosDTO>> {
            emit(UiState.Loading())
            with(todosApiServiceHelper.postTodo(todos)) {
                if (isSuccessful) {
                    emit(UiState.Success(data = this.body()))
                } else {
                    emit(UiState.Error(message = this.message()))
                }
            }
        }.catch {
            emit(UiState.Error(message = it.localizedMessage))
        }

}
