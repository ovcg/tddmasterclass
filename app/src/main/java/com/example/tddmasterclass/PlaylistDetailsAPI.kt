package com.example.tddmasterclass

import com.example.tddmasterclass.details.PlaylistDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaylistDetailsAPI {

    @GET
    suspend fun fetchPlaylistDetails(@Path("id") id: String): PlaylistDetails
}
