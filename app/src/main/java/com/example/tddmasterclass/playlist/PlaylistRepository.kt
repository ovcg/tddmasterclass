package com.example.tddmasterclass.playlist

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class PlaylistRepository {
    suspend fun getPlaylists(): Flow<Result<List<Playlist>>> {
        delay(500)
        return flow {
            Result.success(listOf<Playlist>())
        }
    }
}
