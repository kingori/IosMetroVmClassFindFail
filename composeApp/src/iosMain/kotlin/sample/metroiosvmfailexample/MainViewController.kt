package sample.metroiosvmfailexample

import androidx.compose.ui.window.ComposeUIViewController
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.createGraph
import dev.zacsweers.metrox.viewmodel.ViewModelGraph

fun MainViewController() = ComposeUIViewController {
    val appGraph = createGraph<IosAppGraph>()

    App(appGraph.metroViewModelFactory)
}

@DependencyGraph(AppScope::class)
interface IosAppGraph : ViewModelGraph