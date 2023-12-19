package com.example.tddmasterclass.playlist

import com.example.tddmasterclass.PlaylistService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class PlaylistRepository(
    private val service: PlaylistService
) {
    suspend fun getPlaylists(): Flow<Result<List<Playlist>>> = service.fetchPlaylists()
}
