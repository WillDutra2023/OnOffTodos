package br.com.william.onofftodos.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.william.onofftodos.domain.connection.NetworkConnectionViewModel
import br.com.william.onofftodos.domain.connection.NetworkState
import br.com.william.onofftodos.ui.features.todo.TodoScreen
import br.com.william.onofftodos.ui.features.todo.viewModel.TodoViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object TodoRoute

fun NavGraphBuilder.todoScreen(
    onNavigateToTodoDetail: (id: String) -> Unit,
) {
    composable<TodoRoute> { backStackEntry ->
        val viewModel = koinViewModel<TodoViewModel>()
        val state = viewModel.state.collectAsState().value
        val connectionViewModel = koinViewModel<NetworkConnectionViewModel>()
        val connectionState by connectionViewModel.networkState.collectAsState()
        LaunchedEffect(key1 = Unit) {
            when(connectionState){
                is NetworkState.Lost, NetworkState.Initialization -> {
                    viewModel.getListTodoOffline()
                }
                else -> {
                    viewModel.getListTodo()
                }
            }
        }
        TodoScreen(
            uiState = state,
            onClickToTodoDetail = onNavigateToTodoDetail,
            connectionState
        )
    }

}