package br.com.william.onofftodos.ui.features.detailTodo

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.william.onofftodos.core.UiState
import br.com.william.onofftodos.domain.usecase.GetTodoDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.SavedStateHandle
import br.com.william.onofftodos.domain.connection.NetworkConnectionViewModel
import br.com.william.onofftodos.domain.connection.NetworkState
import br.com.william.onofftodos.domain.model.Todos
import br.com.william.onofftodos.domain.usecase.PatchTodosUseCase
import br.com.william.onofftodos.domain.usecase.db.FindByIdTodosUseCase
import br.com.william.onofftodos.domain.usecase.db.SaveTodosUseCase
import br.com.william.onofftodos.domain.usecase.db.UpdateTodosUseCase
import br.com.william.onofftodos.ui.contracts.TodoDetailContract
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodoDetailViewModel(
    val getTodoDetailUseCase: GetTodoDetailUseCase,
    val patchTodosUseCase: PatchTodosUseCase,
    savedStateHandle: SavedStateHandle,
    val findByIdTodosUseCase: FindByIdTodosUseCase,
    val saveTodosUseCase: SaveTodosUseCase,
    val updateTodosUseCase: UpdateTodosUseCase,
    val viewNetworkConnectionViewModel: NetworkConnectionViewModel,
) : ViewModel() {

    private val id: String? = savedStateHandle["todoId"]

    private val _state: MutableStateFlow<TodoDetailContract> =
        MutableStateFlow(TodoDetailContract())
    val state = _state.asStateFlow()

    fun updateState(newState: TodoDetailContract) {
        _state.value = newState
    }

    init {
        when (viewNetworkConnectionViewModel.networkState.value) {
            is NetworkState.Lost -> {
                Log.d("ID", id.toString())
                id?.let {
                    findByIdDB(it)
                } ?: run {
                    val newState = _state.value.copy(
                        todos = Todos(
                            completed = false,
                            userId = 1,
                            title = ""
                        ), isLoading = false
                    )
                    Log.d("ID", newState.toString())
                    updateState(newState)
                }
            }

            else -> {
                Log.d("ID", id.toString())
                id?.let {
                    getTodo(it)
                } ?: run {

                    val newState = _state.value.copy(
                        todos = Todos(
                            completed = false,
                            userId = 1,
                            title = ""
                        ), isLoading = false
                    )
                    Log.d("ID", newState.toString())
                    updateState(newState)
                }
            }
        }

    }

    fun getTodo(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getTodoDetailUseCase.execute(id).collect {
                when (it) {
                    is UiState.Success -> {
                        val newState = _state.value.copy(todos = it.data!!, isLoading = false)
                        updateState(newState)
                    }

                    is UiState.Error -> {
                        val newState = _state.value.copy(isLoading = false)
                        updateState(newState)
                    }

                    else -> {
                        if (_state.value.isLoading.not()) {
                            val newState = _state.value.copy(isLoading = true)
                            updateState(newState)
                        }
                    }
                }
            }
        }
    }

    fun updateTodo() {
        viewModelScope.launch(Dispatchers.IO) {
            patchTodosUseCase.execute(
                state.value.todos!!
            ).collect {
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
                        if (state.value.isLoading.not()) {
                            val newState = state.value.copy(isLoading = true)
                            updateState(newState)
                        }
                    }
                }
            }
        }
    }

    fun postTodo() {
        viewModelScope.launch(Dispatchers.IO) {
            patchTodosUseCase.execute(
                state.value.todos!!
            ).collect {
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
                        if (state.value.isLoading.not()) {
                            val newState = state.value.copy(isLoading = true)
                            updateState(newState)
                        }
                    }
                }
            }
        }
    }


    fun findByIdDB(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            findByIdTodosUseCase.execute(id).collect {
                when (it) {
                    is UiState.Success -> {
                        Log.d("findByIdDB", it.data.toString())
                        val newState = _state.value.copy(todos = it.data!!, isLoading = false)
                        updateState(newState)
                    }

                    is UiState.Error -> {
                        val newState = state.value.copy(isLoading = false)
                        updateState(newState)
                    }

                    else -> {
                        if (state.value.isLoading.not()) {
                            val newState = state.value.copy(isLoading = true)
                            updateState(newState)
                        }
                    }
                }
            }
        }
    }

    fun saveTodoOff() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("TodoDetailViewModel saveTodoOff",  state.value.todos.toString())
            saveTodosUseCase.execute(
                state.value.todos!!
            ).collect {
                when (it) {
                    is UiState.Success -> {}

                    is UiState.Error -> {
                        val newState = state.value.copy(isLoading = false)
                        updateState(newState)
                    }

                    else -> {
                        if (state.value.isLoading.not()) {
                            val newState = state.value.copy(isLoading = true)
                            updateState(newState)
                        }
                    }
                }
            }
        }
    }

    fun updateTodoOff() {
        viewModelScope.launch(Dispatchers.IO) {
            updateTodosUseCase.execute(
                state.value.todos!!
            ).collect {
                when (it) {
                    is UiState.Success -> {}

                    is UiState.Error -> {
                        val newState = state.value.copy(isLoading = false)
                        updateState(newState)
                    }

                    else -> {
                        if (state.value.isLoading.not()) {
                            val newState = state.value.copy(isLoading = true)
                            updateState(newState)
                        }
                    }
                }
            }
        }
    }

}


