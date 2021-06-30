package com.example.myapplication

import com.example.myapplication.mvvm.ViewModelBase
import com.example.myapplication.mvvm.ViewModelActions
import com.example.myapplication.mvvm.ViewModelState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * A [ViewModelState] for [MainViewModel] / [MainScreen]
 */
data class MainScreenState(
    val query: String = "",
) : ViewModelState

sealed class MainScreenActions : ViewModelActions {
    data class QuerySubmitted(val query: String) : MainScreenActions()
}

/**
 * The [ViewModelBase] for [MainActivity] / [MainScreen].
 */
@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class MainViewModel : ViewModelBase<MainScreenState, MainScreenActions>(MainScreenState()) {

    override fun onAction(action: MainScreenActions) {
        @Exhaustive
        when (action) {
            is MainScreenActions.QuerySubmitted -> setState { copy(query = action.query) }
        }
    }

    fun observeMovies(): Flow<List<Movie>> = state
        .debounce(QUERY_DEBOUNCE_MILLIS)
        .map { state ->
            Movie.MOVIE_TEST_DATA.filter { movie ->
                movie.title.contains(state.query)
            }
        }
        .distinctUntilChanged()

    companion object {
        private const val QUERY_DEBOUNCE_MILLIS = 300L
    }
}