package com.example.myapplication.mvvm

/**
 * Marker interface for events emitted from [ViewModelBase]. Useful
 * for allowing a ViewModel to declaratively request an action.
 *
 * For example:
 * <pre>
 * sealed class MainScreenEvents : ViewModelEvents {
 *     data class SearchIMDBMovieEvent(movie: Movie): MainScreenEvents()
 * }
 * </pre>
 */
interface ViewModelEvents