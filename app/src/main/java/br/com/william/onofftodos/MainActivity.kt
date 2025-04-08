package br.com.william.onofftodos

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import br.com.william.onofftodos.data.work.OnOffWork
import br.com.william.onofftodos.domain.connection.NetworkConnectionViewModel
import br.com.william.onofftodos.domain.connection.NetworkState
import br.com.william.onofftodos.ui.navigation.TodoDetailRoute
import br.com.william.onofftodos.ui.navigation.TodoGraph
import br.com.william.onofftodos.ui.navigation.todoGraph
import br.com.william.onofftodos.ui.theme.OnOffTodosTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.application.rememberKoinApplication
import java.time.Duration
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationHost()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationHost() {
    OnOffTodosTheme {
        val navController = rememberNavController()
        val connectionViewModel = koinViewModel<NetworkConnectionViewModel>()
        val connectionState by connectionViewModel.networkState.collectAsState()
        val snackbarHostState = remember { SnackbarHostState() }
        val context = LocalContext.current
        val message = when(connectionState) {
            is NetworkState.Available, is NetworkState.CapabilitiesChanged -> "com internet"
            NetworkState.Initialization -> null
            is NetworkState.Lost -> "sem internet"
        }
        LaunchedEffect(message) {
            message?.let {
                snackbarHostState.showSnackbar(it,
                    withDismissAction = connectionState is NetworkState.Lost)
            }
        }

        LaunchedEffect(key1 = Unit) {
           val workRequest = PeriodicWorkRequestBuilder<OnOffWork>(
               repeatInterval = 1,
               repeatIntervalTimeUnit = TimeUnit.SECONDS,
               flexTimeInterval = 15,
               flexTimeIntervalUnit = TimeUnit.SECONDS
           ).setBackoffCriteria(
               backoffPolicy = BackoffPolicy.LINEAR,
               duration = Duration.ofSeconds(15)
           ).build()

            val workManager = WorkManager.getInstance(context)
            workManager.enqueue(workRequest)
        }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {TopBarHome(navController)},
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { innerPadding ->
            NavHost(
                modifier = Modifier.padding(innerPadding),
                startDestination = TodoGraph,
                navController = navController
            ) {
                todoGraph(
                    onNavigateToTodoDetail = {
                        navController.navigate(TodoDetailRoute(it))
                    },
                    onPopBackStack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarHome(navController: NavController) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text("ON OFF TODOS")
        },
        actions = {
            IconButton(onClick = {
                navController.navigate(TodoDetailRoute())
            }) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Adicionar Todo",
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarTodoDetail(navController: NavController) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text("ON OFF TODOS")
        },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}

internal fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OnOffTodosTheme {
        NavigationHost()
    }
}