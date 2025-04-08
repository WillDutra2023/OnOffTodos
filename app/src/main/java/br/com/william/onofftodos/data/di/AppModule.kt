package br.com.william.onofftodos.data.di

import android.net.ConnectivityManager
import br.com.william.onofftodos.data.database.TodoDatabase
import br.com.william.onofftodos.data.repositories.TodoDbImpl
import br.com.william.onofftodos.data.repositories.TodosImpl
import br.com.william.onofftodos.data.services.todos.TodosApiHellperImpl
import br.com.william.onofftodos.data.services.todos.TodosApiServiceHelper
import br.com.william.onofftodos.data.services.todos.TodosDbHelperImpl
import br.com.william.onofftodos.data.services.todos.TodosDbServiceHelper
import br.com.william.onofftodos.data.work.OnOffWork
import br.com.william.onofftodos.domain.repositories.TodosDBRepository
import br.com.william.onofftodos.domain.repositories.TodosRespository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val androidModule = module {
    single {
        androidContext()
            .getSystemService(ConnectivityManager::class.java)
                as ConnectivityManager
    }

    worker {OnOffWork(get(), get(), get(), get())}
}

val storageModule = module {
    single {
        TodoDatabase.createDataBase(androidContext())
    }
    factory<TodosDbServiceHelper> { TodosDbHelperImpl(get()) }
}

val repositories = module {
    single<TodosRespository> { TodosImpl(get()) }
    single<TodosDBRepository> { TodoDbImpl(get()) }
}

val serviceHelperModule = module {
    factory<TodosApiServiceHelper> { TodosApiHellperImpl(get()) }
}