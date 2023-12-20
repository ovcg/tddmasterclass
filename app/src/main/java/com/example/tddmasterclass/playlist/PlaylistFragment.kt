package com.example.tddmasterclass.playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.tddmasterclass.PlaylistAPI
import com.example.tddmasterclass.PlaylistService
import com.example.tddmasterclass.R
import retrofit2.Retrofit

class PlaylistFragment : Fragment() {

    private lateinit var viewModel: PlaylistViewModel
    private lateinit var viewModelFactory: PlaylistViewModelFactory

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist_list, container, false)

        setupViewModel()

        viewModel.playlists.observe(this as LifecycleOwner) { playlists ->
            if (playlists.getOrNull() != null) {
                setupList(view, playlists.getOrNull()!!)
            }

        }
        return view
    }

    private fun setupList(
        view: View?,
        playlists: List<Playlist>
    ) {
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyPlaylistRecyclerViewAdapter(playlists)
        }
    }

    private fun setupViewModel() {
        //viewModelFactory = PlaylistViewModelFactory(PlaylistRepository(PlaylistService(PlaylistAPI())))
        viewModel = ViewModelProvider(this, viewModelFactory)[PlaylistViewModel::class.java]
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            PlaylistFragment().apply {

            }
    }
}