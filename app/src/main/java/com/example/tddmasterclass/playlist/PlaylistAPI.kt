package com.example.tddmasterclass.playlist

import com.example.tddmasterclass.PlaylistRaw
import retrofit2.http.GET

interface PlaylistAPI {

    @GET("playlists")
    suspend fun fetchAllPlaylists(): List<PlaylistRaw>
}
