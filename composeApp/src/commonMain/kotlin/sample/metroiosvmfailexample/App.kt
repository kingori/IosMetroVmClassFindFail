package sample.metroiosvmfailexample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.LocalMetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import dev.zacsweers.metrox.viewmodel.metroViewModel
import exmaple.sub.SubItem
import exmaple.sub.SubViewModel
import exmaple.sub.SubVmScreenInSubModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun App(metroVmf: MetroViewModelFactory) {
    MaterialTheme {
        CompositionLocalProvider(LocalMetroViewModelFactory provides metroVmf) {
            var currentScreen by remember { mutableStateOf("home") }

            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .safeContentPadding()
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when (currentScreen) {
                    "home" -> Home(
                        onNavigateToSubVmScreen = { currentScreen = "subVmScreen" },
                        onNavigateToSubVmScreenInSubModule = { currentScreen = "subVmScreenInSubModule" },
                        onNavigateToMainVmScreen = { currentScreen = "mainVmScreen" }
                    )
                    "subVmScreen" -> SubVmScreen(onNavigateBack = { currentScreen = "home" })
                    "subVmScreenInSubModule" -> SubVmScreenInSubModule(onNavigateBack = { currentScreen = "home" })
                    "mainVmScreen" -> MainVmScreen(onNavigateBack = { currentScreen = "home" })
                }
            }
        }
    }
}

@Composable
fun Home(
    onNavigateToSubVmScreen: () -> Unit,
    onNavigateToSubVmScreenInSubModule : () -> Unit,
    onNavigateToMainVmScreen: () -> Unit,
) {
    var showContent by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = onNavigateToSubVmScreen) {
            Text("SubVmScreen")
        }
        Button(onClick = onNavigateToSubVmScreenInSubModule) {
            Text("SubVmScreenInSubModule")
        }
        Button(onClick = onNavigateToMainVmScreen) {
            Text("MainVmScreen")
        }
    }
}

@Composable
fun SubVmScreen(onNavigateBack: () -> Unit, viewModel: SubViewModel = metroViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val count = viewModel.state.collectAsState()

        Text(viewModel.subItem.name)
        Text(count.value.toString())
        Button(onClick = { viewModel.increase() }) {
            Text("Increase")
        }

        Button(onClick = onNavigateBack) {
            Text("Back")
        }
    }
}

@Composable
fun MainVmScreen(onNavigateBack: () -> Unit, viewModel: MainViewModel = metroViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val count = viewModel.state.collectAsState()

        Text(viewModel.subItem.name)
        Text(count.value.toString())
        Button(onClick = { viewModel.increase() }) {
            Text("Increase")
        }

        Button(onClick = onNavigateBack) {
            Text("Back")
        }
    }
}

@Inject
@ViewModelKey(MainViewModel::class)
@ContributesIntoMap(AppScope::class)
class MainViewModel(val subItem: SubItem) : ViewModel() {
    val _state = MutableStateFlow(0)

    val state: StateFlow<Int> = _state.asStateFlow()

    fun increase() {
        _state.value += 1
    }
}
