package com.example.tddmasterclass.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PlaylistDetailsViewModel(
    private val service: PlaylistDetailsService
) : ViewModel() {

    val loader = MutableLiveData<Boolean>()
    val playlistDetails: MutableLiveData<Result<PlaylistDetails>> = MutableLiveData()

    fun getPlaylistDetails(id: String) {
        viewModelScope.launch {
            loader.postValue(true)
            service.fetchPlaylistDetails(id)
                .collect {
                    playlistDetails.postValue(it)
                    loader.postValue(false)
                }
        }
    }
}
