package com.example.tddmasterclass.details

import com.example.tddmasterclass.PlaylistDetailsService
import com.example.tddmasterclass.utils.BaseUnitTest
import com.example.tddmasterclass.utils.getValueForTest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class PlaylistDetailsViewModelShould : BaseUnitTest() {

    private val service: PlaylistDetailsService = mock()
    private lateinit var viewModel: PlaylistDetailsViewModel
    private val id = "1"
    private val playlistDetails: PlaylistDetails = mock()
    private val expected = Result.success(playlistDetails)
    private val exception = RuntimeException("Something went wrong!")
    private val error = Result.failure<PlaylistDetails>(exception)

    @Test
    fun getPlaylistDetailsFromService() = runBlockingTest {
        mockSuccessfulCase()

        verify(service, times(1)).fetchPlaylistDetails(id)
    }


    @Test
    fun emitsPlaylistDetailsFromService() = runBlockingTest {
        mockSuccessfulCase()

        assertEquals(expected, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun emitErrorWhenServiceFails() = runBlockingTest {
        mockErrorCase()

        assertEquals(error, viewModel.playlistDetails.getValueForTest())
    }

    private suspend fun mockSuccessfulCase() {
        `when`(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(Result.success(playlistDetails))
            }
        )

        viewModel = PlaylistDetailsViewModel(service)

        viewModel.getPlaylistDetails(id)
    }

    private suspend fun mockErrorCase() {
        `when`(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(error)
            }
        )

        viewModel = PlaylistDetailsViewModel(service)

        viewModel.getPlaylistDetails(id)
    }
}