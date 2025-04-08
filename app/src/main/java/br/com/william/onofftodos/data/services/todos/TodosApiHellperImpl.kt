package br.com.william.onofftodos.data.services.todos

import br.com.william.onofftodos.data.model.TodosDTO
import br.com.william.onofftodos.data.services.TodosService
import br.com.william.onofftodos.domain.model.Todos
import retrofit2.Response

class TodosApiHellperImpl(private val service: TodosService) : TodosApiServiceHelper {
    override suspend fun getAllTodosListAPI(): Response<List<TodosDTO>> =
        service.getAllTodos()

    override suspend fun getTodosDetailAPI(id: String): Response<TodosDTO> =
        service.getIdTodo(id)

    override suspend fun patchTodo(todo: Todos): Response<TodosDTO> =
        service.patchTodo(id = todo.id.toString(), todos = todo)

    override suspend fun postTodo(todo: Todos): Response<TodosDTO> =
        service.postTodo(todos = todo)


}