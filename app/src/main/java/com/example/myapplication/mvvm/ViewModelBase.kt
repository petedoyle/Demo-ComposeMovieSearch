package com.example.myapplication.mvvm

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.dispatchers.DefaultDispatcherProvider
import com.example.myapplication.dispatchers.DispatcherProvider
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
    protected val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider(),
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    private val _events = MutableSharedFlow<E>()

    val state: StateFlow<S> = _state
    val events: Flow<E> = _events

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun setState(lambda: S.() -> S) {
        _state.value = lambda(_state.value)
    }

    fun postEvent(event: E) {
        viewModelScope.launch(dispatcherProvider.main()) {
            _events.emit(event)
        }
    }

    abstract fun onAction(action: A): Unit
}