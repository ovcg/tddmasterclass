package com.example.tddmasterclass

import com.example.tddmasterclass.playlist.Playlist
import com.example.tddmasterclass.utils.BaseUnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class PlaylistServiceShould : BaseUnitTest() {

    private lateinit var service: PlaylistService
    private val api: PlaylistAPI = mock()
    private val playlists: List<Playlist> = mock()
    private val exception = RuntimeException("Something went wrong")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetchAllPlaylistsFromApi() = runBlockingTest {
        service = PlaylistService(api)

        service.fetchPlaylists()

        verify(api, times(1)).fetchAllPlaylists()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest {
        `when`(api.fetchAllPlaylists()).thenReturn(playlists)

        service = PlaylistService(api)

        assertEquals(Result.success(playlists), service.fetchPlaylists().first())
    }
}