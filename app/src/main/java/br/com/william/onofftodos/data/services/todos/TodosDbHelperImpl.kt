package br.com.william.onofftodos.data.services.todos

import br.com.william.onofftodos.data.database.dao.TodoDao
import br.com.william.onofftodos.data.database.entities.TodoEntity

class TodosDbHelperImpl(private val service: TodoDao) : TodosDbServiceHelper {
    override suspend fun update(todo: TodoEntity) = service.update(todo)

    override suspend fun save(todo: TodoEntity) = service.save(todo)

    override suspend fun saveAll(todo: List<TodoEntity>) = service.saveAll(todo)

    override suspend fun delete(id: Int) = service.delete(TodoEntity(id = id, title = ""))

    override suspend fun findAll() = service.findAll()

    override suspend fun findById(id: String) = service.findById(id)
}
