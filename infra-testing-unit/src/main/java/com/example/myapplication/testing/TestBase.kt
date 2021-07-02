package com.example.myapplication.testing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
abstract class TestBase {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    protected var dispatcherProvider = coroutinesTestRule.testDispatcherProvider

    protected fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
        coroutinesTestRule.testDispatcher.runBlockingTest(block)
}