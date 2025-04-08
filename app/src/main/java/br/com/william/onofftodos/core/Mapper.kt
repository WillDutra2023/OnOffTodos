package br.com.william.onofftodos.core

import android.util.Log
import br.com.william.onofftodos.data.database.entities.TodoEntity
import br.com.william.onofftodos.data.model.TodosDTO
import br.com.william.onofftodos.domain.model.Todos

fun TodosDTO.ToTodoList(): Todos {
    return Todos(
        completed = this.completed,
        id = this.id,
        title = this.title,
        userId = this.userId
    )
}

fun Todos.toTodoEntity() = TodoEntity(
    title = this.title!!,
    completed = this.completed!!
)

fun List<Todos>.toListEntity(): List<TodoEntity> {
    val list = mutableListOf<TodoEntity>()
    for (todos in this) {
        list.add(
            TodoEntity(
                id = todos.id!!,
                title = todos.title!!,
                completed = todos.completed!!
            )
        )
    }

    return list.toList()
}


fun List<TodoEntity>.toListTodo(): List<Todos> {
    Log.d("toListTodo", this.toString())
    val list = mutableListOf<Todos>()
    for (todos in this) {
        list.add(
            Todos(
                id = todos.id,
                title = todos.title,
                completed = todos.completed,
                userId = todos.userId
            )
        )
    }

    return list.toList()
}

fun TodoEntity.toTodo() = Todos(
    id = this.id,
    title = this.title,
    completed = this.completed
)