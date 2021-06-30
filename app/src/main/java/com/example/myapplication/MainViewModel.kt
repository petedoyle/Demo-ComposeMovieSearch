package com.example.myapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

sealed class Actions {
    class QuerySubmitted(val query: String): Actions()
}

data class MainUIState(
    val query: String = "",
)

class MainViewModel : ViewModel<MainUIState>() {

    val uiState: StateFlow = MutableStateFlow(MainUIState())

    fun onAction(action: Actions) {
        when (action) {
            is Actions.QuerySubmitted -> query.value = action.query
        }
    }

    fun observeMovies(): Flow<List<Movie>> = query
        .map { query ->
             Movie.MOVIE_TEST_DATA.filter { movie ->
                 movie.title.contains(query)
             }
        }
}