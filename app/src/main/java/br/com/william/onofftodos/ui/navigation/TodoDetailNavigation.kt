package br.com.william.onofftodos.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import br.com.william.onofftodos.domain.connection.NetworkConnectionViewModel
import br.com.william.onofftodos.ui.features.detailTodo.TodoDetailScreen
import br.com.william.onofftodos.ui.features.detailTodo.TodoDetailViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
data class TodoDetailRoute(val todoId: String? = null)

fun NavGraphBuilder.todoDetailScreen(
    onPopBackStack: () -> Unit
) {
    composable<TodoDetailRoute> { backStackEntry ->
        val todoId = backStackEntry.toRoute<TodoDetailRoute>().todoId
        val viewModel = koinViewModel<TodoDetailViewModel>(
            parameters = { parametersOf(todoId) })
        val uiState = viewModel.state.collectAsState().value
        val connectionViewModel = koinViewModel<NetworkConnectionViewModel>()
        val connectionState by connectionViewModel.networkState.collectAsState()
        TodoDetailScreen(
            state = uiState,
            onChangeTitle = {
                val newTodo = uiState.todos!!.copy(title = it)
                val newState = uiState.copy(todos = newTodo)
                viewModel.updateState(newState)
            },
            onChangeCheck = {
                val newTodo = uiState.todos!!.copy(completed = it)
                val newState = uiState.copy(todos = newTodo)
                viewModel.updateState(newState)
            },
            onUpdate = {
                viewModel.updateTodo()
            },
            onPost = {
                viewModel.postTodo()
            },
            onPopBackStack = {
                onPopBackStack()
            },
            onSaveOff ={
                viewModel.saveTodoOff()
            },
            onUpdateOff = {
                viewModel.updateTodoOff()
            },
            connectionState = connectionState
        )
    }

}