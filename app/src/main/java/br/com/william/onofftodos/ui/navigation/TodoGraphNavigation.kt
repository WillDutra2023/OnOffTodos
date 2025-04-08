package br.com.william.onofftodos.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Serializable
object TodoGraph

fun NavGraphBuilder.todoGraph(
    onNavigateToTodoDetail: (id: String) -> Unit,
    onPopBackStack: () -> Unit
) {
    navigation<TodoGraph>(
        startDestination = TodoRoute
    ) {
        todoScreen(
            onNavigateToTodoDetail
        )
        todoDetailScreen(onPopBackStack = { onPopBackStack() })
    }
}