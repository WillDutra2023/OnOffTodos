package br.com.william.onofftodos.data.services

import br.com.william.onofftodos.data.model.TodosDTO
import br.com.william.onofftodos.domain.model.Todos
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TodosService {
    @GET("/todos")
    suspend fun getAllTodos(): Response<List<TodosDTO>>

    @GET("/todos/{id}")
    suspend fun getIdTodo(@Path("id") id: String): Response<TodosDTO>

    @PATCH("/todos/{id}")
    suspend fun patchTodo(@Path("id") id: String, @Body todos: Todos): Response<TodosDTO>

    @POST("/todos")
    suspend fun postTodo(@Body todos: Todos): Response<TodosDTO>
}