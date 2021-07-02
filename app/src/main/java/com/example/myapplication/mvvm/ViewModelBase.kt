package com.example.myapplication.mvvm

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.example.myapplication.dispatchers.DefaultDispatcherProvider
import com.example.myapplication.dispatchers.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * A [ViewModel] that models UI state using a single [ViewModelState],
 * accepting events from the UI via [ViewModelActions].
 */
abstract class ViewModelBase<S : ViewModelState, A : ViewModelActions>(
    initialState: S,
    protected val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider(),
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)

    val state: StateFlow<S> = _state

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun setState(lambda: S.() -> S) {
        _state.value = lambda(_state.value)
    }

    abstract fun onAction(action: A): Unit
}