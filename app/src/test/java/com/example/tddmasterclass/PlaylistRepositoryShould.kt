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
    private val mapper: PlaylistMapper = mock()
    private val playlists = mock<List<Playlist>>()
    private val playlistsRaw = mock<List<PlaylistRaw>>()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistsFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()

        repository.getPlaylists()

        verify(service, times(1)).fetchPlaylists()
    }

    @Test
    fun emitMappedPlaylistsFromService() = runBlockingTest {
        val repository =  mockSuccessfulCase()

        assertEquals(playlists, repository.getPlaylists().first().getOrNull())
    }

    @Test
    fun propagateErrors() = runBlockingTest {
        val repository = mockFailureCase()

        assertEquals(exception, repository.getPlaylists().first().exceptionOrNull())
    }

    @Test
    fun delegateBusinessLogicToMapper() = runBlockingTest {
        val repository = mockSuccessfulCase()

        repository.getPlaylists()

        verify(mapper, times(1)).invoke(playlistsRaw)
    }

    private suspend fun mockSuccessfulCase(): PlaylistRepository {
        `when`(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.success(playlistsRaw))
            }
        )

        `when`(mapper.invoke(playlistsRaw)).thenReturn(playlists)

        return PlaylistRepository(service, mapper)
    }

    private suspend fun mockFailureCase(): PlaylistRepository {
        `when`(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )

        return PlaylistRepository(service, mapper)
    }
}