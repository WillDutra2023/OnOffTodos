package br.com.william.onofftodos.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class TodoEntity(
    @PrimaryKey
    val id: Int = (300..1000).random(),
    val title: String,
    val completed: Boolean = false,
    val userId: Int = 1
)