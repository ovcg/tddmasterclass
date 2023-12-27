package com.example.tddmasterclass.playlist

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(FragmentComponent::class)
class PlaylistModule {

    @Provides
    fun playListAPI(retrofit: Retrofit): PlaylistAPI = retrofit.create(PlaylistAPI::class.java)

    @Provides
    fun retrofit() = Retrofit.Builder()
        .baseUrl("http://192.168.15.82:3001/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}