package com.example.tddmasterclass.playlist

import com.example.tddmasterclass.R
import javax.inject.Inject

class PlaylistMapper@Inject constructor() : Function1<List<PlaylistRaw>, List<Playlist>> {
    override fun invoke(playlistsRaw: List<PlaylistRaw>): List<Playlist> {
        return playlistsRaw.map {
            Playlist(
                id = it.id,
                name = it.name,
                category = it.category,
                image = getImage(it.category)
            )
        }
    }

    private fun getImage(category: String): Int {
        return if (category == "rock") {
            R.mipmap.rock
        } else {
            R.mipmap.playlist
        }
    }
}
