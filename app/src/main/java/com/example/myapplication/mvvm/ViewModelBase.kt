package com.example.myapplication.mvvm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * A [ViewModel] that models UI state using a single [ViewModelState],
 * accepting events from the UI via [ViewModelActions].
 */
abstract class ViewModelBase<S : ViewModelState, A : ViewModelActions>(
    initialState: S,
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state

    internal fun setState(lambda: S.() -> S) {
        _state.value = lambda(_state.value)
    }

    abstract fun onAction(action: A): Unit
}