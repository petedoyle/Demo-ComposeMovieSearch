package com.example.myapplication

import androidx.lifecycle.viewModelScope
import app.cash.exhaustive.Exhaustive
import com.example.myapplication.mvvm.ViewModelBase
import com.example.myapplication.mvvm.ViewModelActions
import com.example.myapplication.mvvm.ViewModelEvents
import com.example.myapplication.mvvm.ViewModelState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * A [ViewModelState] for [MainViewModel] / [MainScreen]
 */
data class MainScreenState(
    val query: String = "",
    val searchResults: List<Movie> = listOf(),
) : ViewModelState

sealed class MainScreenActions : ViewModelActions {
    data class QuerySubmitted(val query: String) : MainScreenActions()
}

sealed class MainScreenEvents : ViewModelEvents

/**
 * The [ViewModelBase] for [MainActivity] / [MainScreen].
 */
@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class MainViewModel : ViewModelBase<MainScreenState, MainScreenActions, MainScreenEvents>(
    MainScreenState()
) {

    init {
        viewModelScope.launch {
            state
                .debounce(QUERY_DEBOUNCE_MILLIS)
                .map { state ->
                    Movie.MOVIE_TEST_DATA.filter { movie ->
                        movie.title.contains(state.query)
                    }
                }
                .distinctUntilChanged()
                .collect { results ->
                    setState { copy(searchResults = results) }
                }
        }
    }

    override fun onAction(action: MainScreenActions) {
        @Exhaustive
        when (action) {
            is MainScreenActions.QuerySubmitted -> setState { copy(query = action.query) }
        }
    }

    companion object {
        private const val QUERY_DEBOUNCE_MILLIS = 300L
    }
}