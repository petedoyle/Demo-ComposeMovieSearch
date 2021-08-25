/*
 * Copyright 2021 Pete Doyle <petedoyle@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.petedoyle.sample.moviesearch.mvvm

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import dev.petedoyle.sample.moviesearch.dispatchers.DefaultDispatcherProvider
import dev.petedoyle.sample.moviesearch.dispatchers.DispatcherProvider
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