package br.com.william.onofftodos.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.william.onofftodos.data.database.entities.TodoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TodoDao {

    @Query("SELECT * FROM TodoEntity")
    fun findAll(): List<TodoEntity>

    @Query("SELECT * FROM TodoEntity WHERE id = :id")
    fun findById(id: String): TodoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(todo: TodoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(todo: List<TodoEntity>)

    @Delete
    suspend fun delete(todo: TodoEntity)

    @Update
    suspend fun update(todo: TodoEntity)

}