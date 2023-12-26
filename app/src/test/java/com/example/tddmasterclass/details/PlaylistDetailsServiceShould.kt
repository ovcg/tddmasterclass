package com.example.tddmasterclass.details

import com.example.tddmasterclass.PlaylistDetailsAPI
import com.example.tddmasterclass.PlaylistDetailsService
import com.example.tddmasterclass.utils.BaseUnitTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class PlaylistDetailsServiceShould : BaseUnitTest() {

    lateinit var service: PlaylistDetailsService
    private val id = "100"
    private val api: PlaylistDetailsAPI = mock()
    private val playlistDetails: PlaylistDetails = mock()
    private val expected = Result.success(playlistDetails)
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun fetchPlaylistDetailsFromAPI() = runBlockingTest {
        service = PlaylistDetailsService(api)

        service.fetchPlaylistDetails(id).single()

        verify(api, times(1)).fetchPlaylistDetails(id)
    }

    @Test
    fun convertValuesToFlowResultAndEmitThem() = runBlockingTest {
        mockSuccessfulCase()

        assertEquals(expected, service.fetchPlaylistDetails(id).single())
    }

    @Test
    fun emitsErrorResultWhenNetworkFails() = runBlockingTest {
        mockErrorCase()

        assertEquals(
            "Something went wrong",
            service.fetchPlaylistDetails(id).first().exceptionOrNull()?.message
        )
    }

    private suspend fun mockErrorCase() {
        `when`(api.fetchPlaylistDetails(id)).thenThrow(exception)

        service = PlaylistDetailsService(api)

        service.fetchPlaylistDetails(id)
    }

    private suspend fun mockSuccessfulCase() {
        `when`(api.fetchPlaylistDetails(id)).thenReturn(playlistDetails)

        service = PlaylistDetailsService(api)
    }
}