package br.com.william.onofftodos.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.william.onofftodos.data.database.dao.TodoDao
import br.com.william.onofftodos.data.database.entities.TodoEntity


@Database(entities = [TodoEntity::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {
        fun createDataBase(context: Context) : TodoDao {
            return Room
                .databaseBuilder(context, TodoDatabase::class.java, "todo.db")
                .allowMainThreadQueries()
                .build()
                .todoDao()
        }
    }

}