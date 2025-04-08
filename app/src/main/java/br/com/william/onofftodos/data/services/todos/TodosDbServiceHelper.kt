package br.com.william.onofftodos.data.services.todos

import br.com.william.onofftodos.data.database.entities.TodoEntity
import br.com.william.onofftodos.domain.model.Todos

interface TodosDbServiceHelper {
    suspend fun update(todo: TodoEntity)
    suspend fun save(todo: TodoEntity)
    suspend fun saveAll(todo: List<TodoEntity>)
    suspend fun delete(id:Int)
    suspend fun findAll(): List<TodoEntity>
    suspend fun findById(id: String): TodoEntity
}