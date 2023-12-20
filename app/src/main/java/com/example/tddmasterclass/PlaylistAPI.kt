package com.example.tddmasterclass

import com.example.tddmasterclass.playlist.Playlist
import retrofit2.http.GET

interface PlaylistAPI {

    @GET("playlists")
    suspend fun fetchAllPlaylists(): List<Playlist>
}
