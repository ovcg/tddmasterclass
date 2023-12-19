package com.example.tddmasterclass

import com.example.tddmasterclass.playlist.Playlist

interface PlaylistAPI {

    fun fetchAllPlaylists(): List<Playlist>
}
