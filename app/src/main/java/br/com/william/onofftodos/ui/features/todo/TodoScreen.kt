package br.com.william.onofftodos.ui.features.todo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.william.onofftodos.R
import br.com.william.onofftodos.domain.connection.NetworkState
import br.com.william.onofftodos.ui.components.EmptyView
import br.com.william.onofftodos.ui.components.LoadingBar
import br.com.william.onofftodos.ui.contracts.TodosContract
import br.com.william.onofftodos.ui.features.components.CardList

@Composable
fun TodoScreen(
    uiState: TodosContract,
    onClickToTodoDetail: (id: String) -> Unit,
    connectionState: NetworkState
) {
    Box {
        val listTodo = uiState.todos
        if (uiState.isLoading) {
            return LoadingBar()
        }
        if (listTodo.isEmpty()) {
            EmptyView(message = stringResource(R.string.loading_data))
        } else {
            LazyColumn(
                state = rememberLazyListState(),
                contentPadding = PaddingValues(10.dp),
                modifier = Modifier.fillMaxSize(),
                content = {
                    items(
                        key = { item ->
                            listTodo[item].id!!
                        },
                        count = listTodo.size,
                        itemContent = { item ->
                            val todo = listTodo[item]
                            CardList(todo, onClickToTodoDetail)
                        }
                    )
                }
            )
        }
    }
}