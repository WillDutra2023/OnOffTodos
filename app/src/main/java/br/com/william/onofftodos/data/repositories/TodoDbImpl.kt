package br.com.william.onofftodos.data.repositories

import android.util.Log
import br.com.william.onofftodos.core.UiState
import br.com.william.onofftodos.core.toListEntity
import br.com.william.onofftodos.core.toTodoEntity
import br.com.william.onofftodos.data.database.entities.TodoEntity
import br.com.william.onofftodos.data.services.todos.TodosDbServiceHelper
import br.com.william.onofftodos.domain.model.Todos
import br.com.william.onofftodos.domain.repositories.TodosDBRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

class TodoDbImpl(
    private val todosDbServiceHelper: TodosDbServiceHelper
) : TodosDBRepository, KoinComponent {
    override suspend fun save(todo: Todos) = flow<UiState<Unit>> {
        emit(UiState.Loading())
        Log.d("TodoDbImpl", todo.title.toString())
        with(todosDbServiceHelper.save(todo.toTodoEntity())) {
            emit(UiState.Success(data = this))
        }
    }.catch {
        emit(UiState.Error(message = it.localizedMessage))
    }

    override suspend fun saveAll(todo: List<Todos>)= flow<UiState<Unit>> {
        emit(UiState.Loading())
        Log.d("TodoDbImpl", "saveAll")
        with(todosDbServiceHelper.saveAll(todo.toListEntity())) {
            emit(UiState.Success(data = this))
        }
    }.catch {
        emit(UiState.Error(message = it.localizedMessage))
    }



    override suspend fun delete(id: Int): Flow<UiState<Unit>> = flow<UiState<Unit>> {
        emit(UiState.Loading())
        with(todosDbServiceHelper.delete(id)) {
            emit(UiState.Success(data = this))
        }
    }.catch {
        emit(UiState.Error(message = it.localizedMessage))
    }

    override suspend fun findAll(): Flow<UiState<List<TodoEntity>>> = flow<UiState<List<TodoEntity>>> {
        emit(UiState.Loading())
        with(todosDbServiceHelper.findAll()) {
            emit(UiState.Success(data = this))
        }
    }.catch {
        emit(UiState.Error(message = it.localizedMessage))
    }

    override suspend fun findById(id: String): Flow<UiState<TodoEntity>> = flow<UiState<TodoEntity>> {
        emit(UiState.Loading())
        Log.d("TodoDbImpl ID", id.toString())
        with(todosDbServiceHelper.findById(id)) {
            Log.d("TodoDbImpl", this.toString())
            emit(UiState.Success(data = this))
        }
    }.catch {
        emit(UiState.Error(message = it.localizedMessage))
    }

    override suspend fun update(todo: Todos) = flow<UiState<Unit>> {
        emit(UiState.Loading())
        Log.d("TodoDbImpl", todo.id.toString())
        with(todosDbServiceHelper.update(todo.toTodoEntity())) {
            emit(UiState.Success(data = this))
        }
    }.catch {
        emit(UiState.Error(message = it.localizedMessage))
    }


}