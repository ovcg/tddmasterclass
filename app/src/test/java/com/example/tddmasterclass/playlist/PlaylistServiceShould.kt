package com.example.tddmasterclass.playlist

import com.example.tddmasterclass.playlist.PlaylistAPI
import com.example.tddmasterclass.playlist.PlaylistRaw
import com.example.tddmasterclass.playlist.PlaylistService
import com.example.tddmasterclass.utils.BaseUnitTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class PlaylistServiceShould : BaseUnitTest() {

    private lateinit var service: PlaylistService
    private val api: PlaylistAPI = mock()
    private val playlists: List<PlaylistRaw> = mock()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest {
        mockSuccessfulCase()

        assertEquals(Result.success(playlists), service.fetchPlaylists().first())
    }

    @Test
    fun emitsErrorResultWhenNetworkFails() = runBlockingTest {

        mockErrorCase()

        assertEquals("Something went wrong",
            service.fetchPlaylists().first().exceptionOrNull()?.message)
    }

    private suspend fun mockErrorCase() {
        `when`(api.fetchAllPlaylists()).thenThrow(exception)

        service = PlaylistService(api)
    }

    private suspend fun mockSuccessfulCase() {
        `when`(api.fetchAllPlaylists()).thenReturn(playlists)

        service = PlaylistService(api)
    }
}