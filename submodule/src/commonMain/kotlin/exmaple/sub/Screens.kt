package exmaple.sub

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.zacsweers.metrox.viewmodel.metroViewModel


@Composable
fun SubVmScreenInSubModule(onNavigateBack: () -> Unit, viewModel: SubViewModel = metroViewModel()) {
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