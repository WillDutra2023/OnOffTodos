package br.com.william.onofftodos.domain.di

import br.com.william.onofftodos.domain.connection.NetworkConnectionViewModel
import br.com.william.onofftodos.ui.features.detailTodo.TodoDetailViewModel
import br.com.william.onofftodos.ui.features.todo.viewModel.TodoViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TodoViewModel(get(), get(), get()) }
    viewModel { TodoDetailViewModel(get(), get(), get(), get(), get(), get(), get()) }
    viewModel { NetworkConnectionViewModel(get()) }
}