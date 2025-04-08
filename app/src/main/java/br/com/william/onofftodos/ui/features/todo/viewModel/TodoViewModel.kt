package br.com.william.onofftodos.ui.features.todo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.william.onofftodos.core.UiState
import br.com.william.onofftodos.domain.connection.NetworkConnectionViewModel
import br.com.william.onofftodos.domain.connection.NetworkState
import br.com.william.onofftodos.domain.usecase.GetTodosUseCase
import br.com.william.onofftodos.domain.usecase.db.FindAllTodosUseCase
import br.com.william.onofftodos.ui.contracts.TodosContract
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoViewModel(
    private val todosUseCase: GetTodosUseCase,
    private val findAllTodosUseCase: FindAllTodosUseCase,
    private val viewModel: NetworkConnectionViewModel
) : ViewModel() {

    private val _state = MutableStateFlow(TodosContract())
    val state: StateFlow<TodosContract> = _state.asStateFlow()

    private fun updateState(newState: TodosContract) {
        _state.value = newState
    }

    fun getListTodo() {
        viewModelScope.launch(IO) {
            todosUseCase.execute().collect {
                when (it) {
                    is UiState.Success -> {
                        val newState = _state.value.copy(todos = it.data!!, isLoading = false)
                        updateState(newState)
                    }

                    is UiState.Error -> {
                        val newState = state.value.copy(isLoading = false)
                        updateState(newState)
                    }

                    else -> {
                        if (!state.value.isLoading) {
                            val newState = state.value.copy(isLoading = true)
                            updateState(newState)
                        }
                    }
                }
            }
        }
    }

    fun getListTodoOffline() {
        viewModelScope.launch(IO) {
            findAllTodosUseCase.execute().collect {
                when (it) {
                    is UiState.Success -> {
                        val newState = _state.value.copy(todos = it.data!!, isLoading = false)
                        updateState(newState)
                    }

                    is UiState.Error -> {
                        val newState = state.value.copy(isLoading = false)
                        updateState(newState)
                    }

                    else -> {
                        if (!state.value.isLoading) {
                            val newState = state.value.copy(isLoading = true)
                            updateState(newState)
                        }
                    }
                }
            }
        }
    }

}