package br.com.william.onofftodos.data.services.todos

import br.com.william.onofftodos.data.model.TodosDTO
import br.com.william.onofftodos.domain.model.Todos
import retrofit2.Response

interface TodosApiServiceHelper {
    suspend fun getAllTodosListAPI(): Response<List<TodosDTO>>
    suspend fun getTodosDetailAPI(id: String): Response<TodosDTO>
    suspend fun patchTodo(todo: Todos): Response<TodosDTO>
    suspend fun postTodo(todo: Todos): Response<TodosDTO>
}