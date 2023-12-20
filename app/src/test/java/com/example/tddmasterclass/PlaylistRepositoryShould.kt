package com.example.tddmasterclass

import com.example.tddmasterclass.playlist.Playlist
import com.example.tddmasterclass.playlist.PlaylistRepository
import com.example.tddmasterclass.playlist.PlaylistService
import com.example.tddmasterclass.utils.BaseUnitTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class PlaylistRepositoryShould : BaseUnitTest() {

    private val service: PlaylistService = mock()
    private val playlists = mock<List<Playlist>>()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistsFromService() = runBlockingTest {
        val repository = PlaylistRepository(service)

        repository.getPlaylists()

        verify(service, times(1)).fetchPlaylists()
    }

    @Test
    fun emitPlaylistsFromService() = runBlockingTest {
        val repository =  mockSuccessfulCase()

        assertEquals(playlists, repository.getPlaylists().first().getOrNull())
    }

    @Test
    fun propagateErrors() = runBlockingTest {
        `when`(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )
    }

    private suspend fun mockSuccessfulCase(): PlaylistRepository {
        `when`(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.success(playlists))
            }
        )

        return PlaylistRepository(service)
    }
}