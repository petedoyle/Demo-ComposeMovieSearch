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
package com.example.myapplication

import com.example.myapplication.testing.TestBase
import com.nhaarman.expect.expect
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainScreenViewModelTest : TestBase() {
    private lateinit var viewModel: MainScreenViewModel

    @Before
    fun setup() {
        viewModel = MainScreenViewModel()
    }

    @Test
    fun `Results should be unfiltered when no query has been entered`() = runBlockingTest {
        // Given
        viewModel.setState { copy(query = "") }

        // When
        advanceTimeBy(MainScreenViewModel.QUERY_DEBOUNCE_MILLIS) // required since we debounce before querying

        // Then
        with(viewModel.state.value) {
            expect(searchResults).toHaveSize(Movie.MOVIE_TEST_DATA.size)
        }
    }

    @Test
    fun `Results should be filtered when a query has been entered`() = runBlockingTest {
        // Given
        viewModel.setState { copy(query = "star") }

        // When
        advanceTimeBy(MainScreenViewModel.QUERY_DEBOUNCE_MILLIS) // required since we debounce before querying

        // Then
        with(viewModel.state.value) {
            expect(searchResults.size).toBeGreaterThan(0)
            expect(searchResults).toBe(
                Movie.MOVIE_TEST_DATA.filter {
                    it.title.contains(viewModel.state.value.query, ignoreCase = true)
                }
            )
        }
    }

    @Test
    fun `Query should be cleared when clear button clicked`() = runBlockingTest {
        // Given
        viewModel.setState { copy(query = "star") }
        advanceTimeBy(MainScreenViewModel.QUERY_DEBOUNCE_MILLIS) // required since we debounce before querying

        // When
        viewModel.onAction(MainScreenActions.QueryCleared)

        // Then
        expect(viewModel.state.value.query).toBe("")
    }

    @Test
    fun `Focusing movie action should set state`() = runBlockingTest {
        // Given
        val focusedMovie = Movie("Ace Ventura: Pet Detective", "1994")

        // When
        viewModel.onAction(MainScreenActions.MovieFocused(focusedMovie))

        // Then
        expect(viewModel.state.value.focusedMovie).toBe(focusedMovie)
    }

    @Test
    fun `Focusing on a movie should clear state`() = runBlockingTest {
        // Given
        viewModel.setState { copy(focusedMovie = Movie("Ace Ventura: Pet Detective", "1994")) }

        // When
        viewModel.onAction(MainScreenActions.MovieBlurred)

        // Then
        expect(viewModel.state.value.focusedMovie).toBeNull()
    }
}