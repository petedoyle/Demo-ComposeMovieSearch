package com.example.myapplication.mvvm

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * A [ViewModel] that models UI state using a single [ViewModelState],
 * accepting events from the UI via [ViewModelActions].
 */
abstract class ViewModelBase<S : ViewModelState, A : ViewModelActions, E : ViewModelEvents>(
    initialState: S,
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state

    private val _events = MutableSharedFlow<E>()
    val events: Flow<E> = _events

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal fun setState(lambda: S.() -> S) {
        _state.value = lambda(_state.value)
    }

    fun postEvent(event: E) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }

    abstract fun onAction(action: A): Unit
}