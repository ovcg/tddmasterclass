package com.example.tddmasterclass

import com.example.tddmasterclass.playlist.Playlist
import com.example.tddmasterclass.playlist.PlaylistRepository
import com.example.tddmasterclass.playlist.PlaylistViewModel
import com.example.tddmasterclass.utils.BaseUnitTest
import com.example.tddmasterclass.utils.captureValues
import com.example.tddmasterclass.utils.getValueForTest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class PlaylistViewModelShould : BaseUnitTest() {

    private val repository: PlaylistRepository = mock(PlaylistRepository::class.java)
    private val playlists = mock<List<Playlist>>()
    private val expected = Result.success(playlists)
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistsFromRepository() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.playlists.getValueForTest()

        verify(repository, times(1)).getPlaylists()
    }

    @Test
    fun emitsPlaylistsFromRepository() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        assertEquals(expected, viewModel.playlists.getValueForTest())
    }


    @Test
    fun emitErrorWhenReceiveError() {
        val viewModel = mockErrorCase()

        assertEquals(exception, viewModel.playlists.getValueForTest()!!.exceptionOrNull()!!)
    }

    @Test
    fun showSpinnerWhileLoading() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()

            assertEquals(true, values[0])
        }
    }

    @Test
    fun closeLoaderAfterPlaylistsLoad() = runBlockingTest{
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()
            assertEquals(false, values.last())
        }
    }

    private fun mockErrorCase(): PlaylistViewModel {
        runBlocking {
            Mockito.`when`(repository.getPlaylists()).thenReturn(
                flow {
                    emit(Result.failure<List<Playlist>>(exception))
                }
            )
        }

        return PlaylistViewModel(repository)
    }

    private fun mockSuccessfulCase(): PlaylistViewModel {
        runBlocking {
            Mockito.`when`(repository.getPlaylists()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }

        return PlaylistViewModel(repository)
    }
}