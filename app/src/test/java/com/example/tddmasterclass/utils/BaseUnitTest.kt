package com.example.tddmasterclass.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Before
import org.junit.Rule
import org.mockito.MockitoAnnotations

open class BaseUnitTest {

    @get: Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

}