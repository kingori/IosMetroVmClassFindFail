# MetroDI iOS ViewModel Resolution Failure Example

This project demonstrates an issue where MetroDI's `compose-viewmodel` integration fails to resolve a `ViewModel` on the iOS platform when the `ViewModel` is defined in a separate submodule.

The purpose of this project is to highlight a scenario where an exception occurs on the iOS platform when using the compose viewmodel feature of MetroDI (https://github.com/ZacSweers/metro/tree/main).

## The Problem

When using `metro-compose-view-model` for dependency injection with Jetpack/JetBrains Compose `viewModel()` factory, an `IllegalArgumentException` is thrown on iOS if the `ViewModel` class resides in a different Gradle module from the Composable that injects it.

This issue appears to be specific to the iOS target, as the same setup works correctly on Android and JVM platforms.

### Exception Log

The following exception is thrown when attempting to navigate to a screen that injects a `ViewModel` from a submodule:

```
kotlin.IllegalArgumentException: Unknown model class class exmaple.sub.SubViewModel
Uncaught Kotlin exception:     at 0   MetroIosVMFailExample.debug.dylib   0x103d3507b        kfun:kotlin.Throwable#<init>(kotlin.String?){} + 99 
    at 1   MetroIosVMFailExample.debug.dylib   0x103d2f9db        kfun:kotlin.Exception#<init>(kotlin.String?){} + 95 
    at 2   MetroIosVMFailExample.debug.dylib   0x103d2fb9b        kfun:kotlin.RuntimeException#<init>(kotlin.String?){} + 95 
    at 3   MetroIosVMFailExample.debug.dylib   0x103d2fe73        kfun:kotlin.IllegalArgumentException#<init>(kotlin.String?){} + 95 
    at 4   MetroIosVMFailExample.debug.dylib   0x103f4c347        kfun:dev.zacsweers.metrox.viewmodel.MetroViewModelFactory#create(kotlin.reflect.KClass<0:0>;androidx.lifecycle.viewmodel.CreationExtras){0§<androidx.lifecycle.ViewModel>}0:0 + 787 
    at 5   MetroIosVMFailExample.debug.dylib   0x103f4bad7        kfun:androidx.lifecycle.ViewModelProvider.Factory#create(kotlin.reflect.KClass<0:0>;androidx.lifecycle.viewmodel.CreationExtras){0§<androidx.lifecycle.ViewModel>}0:0-trampoline + 115 
    at 6   MetroIosVMFailExample.debug.dylib   0x103f4a96b        kfun:androidx.lifecycle.viewmodel#createViewModel(androidx.lifecycle.ViewModelProvider.Factory;kotlin.reflect.KClass<0:0>;androidx.lifecycle.viewmodel.CreationExtras){0§<androidx.lifecycle.ViewModel>}0:0 + 131 
    at 7   MetroIosVMFailExample.debug.dylib   0x103f48743        kfun:androidx.lifecycle.viewmodel.ViewModelProviderImpl#getViewModel(kotlin.reflect.KClass<0:0>;kotlin.String){0§<androidx.lifecycle.ViewModel>}0:0 + 1143 
    at 8   MetroIosVMFailExample.debug.dylib   0x103f4898f        kfun:androidx.lifecycle.viewmodel.ViewModelProviderImpl#getViewModel$default(kotlin.reflect.KClass<0:0>;kotlin.String?;kotlin.Int){0§<androidx.lifecycle.ViewModel>}0:0 + 283 
    at 9   MetroIosVMFailExample.debug.dylib   0x103f49eff        kfun:androidx.lifecycle.ViewModelProvider#get(kotlin.reflect.KClass<0:0>){0§<androidx.lifecycle.ViewModel>}0:0 + 171 
    at 10  MetroIosVMFailExample.debug.dylib   0x1044e568b        kfun:androidx.lifecycle.viewmodel.compose#get__at__androidx.lifecycle.ViewModelStoreOwner(kotlin.reflect.KClass<0:0>;kotlin.String?;androidx.lifecycle.ViewModelProvider.Factory?;androidx.lifecycle.viewmodel.CreationExtras){0§<androidx.lifecycle.ViewModel>}0:0 + 687 
    at 11  MetroIosVMFailExample.debug.dylib   0x1044e535b        kfun:androidx.lifecycle.viewmodel.compose#viewModel(kotlin.reflect.KClass<0:0>;androidx.lifecycle.ViewModelStoreOwner?;kotlin.String?;androidx.lifecycle.ViewModelProvider.Factory?;androidx.lifecycle.viewmodel.CreationExtras?;androidx.compose.runtime.Composer?;kotlin.Int;kotlin.Int){0§<androidx.lifecycle.ViewModel>}0:0 + 1319 
    at 12  MetroIosVMFailExample.debug.dylib   0x1034562cf        kfun:exmaple.sub#SubVmScreenInSubModule(kotlin.Function0<kotlin.Unit>;exmaple.sub.SubViewModel?;androidx.compose.runtime.Composer?;kotlin.Int;kotlin.Int){} + 4379 
```

## Scenarios

The sample app includes three buttons to demonstrate the different scenarios:

1.  **MainVmScreen Button**: This works correctly. It navigates to a screen that injects a `ViewModel` defined within the *same* module (`:composeApp`).
2.  **SubVmScreen Button**: This **crashes the app**. It attempts to navigate to a screen in `:composeApp` that injects a `ViewModel` defined in a *submodule* (`:submodule`).
3.  **SubVmScreenInSubModel Button**: This also **crashes the app**. It demonstrates that the crash also occurs when the Composable itself is located in the submodule along with its `ViewModel`.

## Steps to Reproduce

1.  Run the app on an iOS target (simulator or device).
2.  Tap the "SubVmScreen" button.
3.  Observe the app crash.

## Environment

-   **Platform**: iOS
-   **Library**: MetroDI (`dev.zacsweers.metro:metro-compose-view-model`)

The issue does not occur on Android or JVM targets.
