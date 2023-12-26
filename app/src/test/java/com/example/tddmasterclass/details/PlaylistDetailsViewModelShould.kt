package com.example.tddmasterclass.details

import com.example.tddmasterclass.utils.BaseUnitTest
import com.example.tddmasterclass.utils.captureValues
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
    private val id = "1"
    private val playlistDetails: PlaylistDetails = mock()
    private val expected = Result.success(playlistDetails)
    private val exception = RuntimeException("Something went wrong!")
    private val error = Result.failure<PlaylistDetails>(exception)

    @Test
    fun getPlaylistDetailsFromService() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.getPlaylistDetails(id)

        verify(service, times(1)).fetchPlaylistDetails(id)
    }


    @Test
    fun emitsPlaylistDetailsFromService() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.getPlaylistDetails(id)

        assertEquals(expected, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun emitErrorWhenServiceFails() = runBlockingTest {
        val viewModel = mockErrorCase()

        viewModel.getPlaylistDetails(id)
        assertEquals(error, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun showSpinnerWhileLoading() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.getPlaylistDetails(id)

        viewModel.loader.captureValues {
            viewModel.playlistDetails.getValueForTest()
            assertEquals(true, values[0])
        }
    }

    @Test
    fun closeLoaderAfterPlaylistDetailsLoad() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.getPlaylistDetails(id)

        viewModel.loader.captureValues {
            assertEquals(false, values.last())
        }
    }

    private suspend fun mockSuccessfulCase(): PlaylistDetailsViewModel {
        `when`(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(Result.success(playlistDetails))
            }
        )

        return PlaylistDetailsViewModel(service)
    }

    private suspend fun mockErrorCase(): PlaylistDetailsViewModel {
        `when`(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(error)
            }
        )

        return PlaylistDetailsViewModel(service)
    }
}