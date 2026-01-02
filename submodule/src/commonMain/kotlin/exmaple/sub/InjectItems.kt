@file:OptIn(ExperimentalObjCName::class)

package exmaple.sub

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@Inject
class SubItem {
    val name: String = "subItem"
}

@Inject
@ViewModelKey(SubViewModel::class)
@ContributesIntoMap(AppScope::class)
@ObjCName("SubViewModel")
class SubViewModel( val subItem: SubItem) : ViewModel() {
    val _state = MutableStateFlow(0)

    val state : StateFlow<Int> =  _state.asStateFlow()

    fun increase() {
        _state.value += 1
    }
}
