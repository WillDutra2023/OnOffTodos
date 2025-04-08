package br.com.william.onofftodos.data.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.william.onofftodos.core.UiState
import br.com.william.onofftodos.domain.model.Todos
import br.com.william.onofftodos.domain.usecase.PostTodosUseCase
import br.com.william.onofftodos.domain.usecase.db.FindAllTodosUseCase

class OnOffWork(
    context: Context,
    private val workParam: WorkerParameters,
    private val findAllTodosUseCase: FindAllTodosUseCase,
    private val postTodosUseCase: PostTodosUseCase
) : CoroutineWorker(context, workParam) {
    override suspend fun doWork(): Result {
        Log.d("OnOffWork", "ENTROU NO WORK")
        findAllTodosUseCase.execute().collect {
            when (it) {
                is UiState.Success -> {
                    sendTodos(it.data!!)
                }

                is UiState.Error -> {}

                else -> {}
            }
        }
        return Result.success()
    }

    suspend fun sendTodos(listTodos: List<Todos>) {
        for (todo in listTodos) {
            postTodosUseCase.execute(todo).collect {
                when (it) {
                    is UiState.Success -> {
                        Log.d("OnOffWork", "Eviado Todo ${todo.title}")
                    }

                    is UiState.Error -> {}

                    else -> {}
                }
            }
        }
    }
}