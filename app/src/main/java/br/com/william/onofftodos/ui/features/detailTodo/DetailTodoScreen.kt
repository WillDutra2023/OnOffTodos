package br.com.william.onofftodos.ui.features.detailTodo

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.william.onofftodos.domain.connection.NetworkState
import br.com.william.onofftodos.ui.components.ButtonOutline
import br.com.william.onofftodos.ui.components.CustomTextField
import br.com.william.onofftodos.ui.components.LoadingBar
import br.com.william.onofftodos.ui.contracts.TodoDetailContract

@Composable
fun TodoDetailScreen(
    state: TodoDetailContract,
    onChangeTitle: (String) -> Unit,
    onChangeCheck: (Boolean) -> Unit,
    onUpdate: () -> Unit,
    onPost: () -> Unit,
    onPopBackStack: () -> Unit,
    connectionState: NetworkState,
    onSaveOff: () -> Unit,
    onUpdateOff: () -> Unit,
) {

    fun lostConnection() {
        Log.d("lostConnection Id", state.todos?.id.toString())
        if (state.todos?.id == null) {
            onSaveOff()
        } else {
            onUpdateOff()
        }
    }

    fun onConnection() {
        if (state.todos?.id == null) {
            onPost()
        } else {
            onUpdate()
        }
    }


    if (state.isLoading) {
        return LoadingBar()
    } else {
        Column {
            CustomTextField(
                label = "Titulo",
                value = state.todos?.title ?: "",
                onValueChange = {
                    onChangeTitle(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text("Completado")
                Checkbox(
                    checked = state.todos?.completed == true,
                    onCheckedChange = {
                        onChangeCheck(it)
                    },
                )
            }
            ButtonOutline(
                text = "Salvar",
                onClick = {
                    Log.d("connectionState", connectionState.toString())
                    when (connectionState) {
                        is NetworkState.Lost, NetworkState.Initialization  -> {
                            lostConnection()
                        }
                        else -> {
                            onConnection()
                        }
                    }

                    onPopBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }
    }

}

