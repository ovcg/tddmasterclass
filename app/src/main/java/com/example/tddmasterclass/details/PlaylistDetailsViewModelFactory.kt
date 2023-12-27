package com.example.tddmasterclass.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class PlaylistDetailsViewModelFactory @Inject constructor(
    private val repository: PlaylistDetailsService
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlaylistDetailsViewModel(repository) as T
    }
}
