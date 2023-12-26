package com.example.tddmasterclass.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tddmasterclass.R
import com.example.tddmasterclass.utils.TestIdlingResource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistFragment : Fragment() {

    private lateinit var viewModel: PlaylistViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist_list, container, false)

        setupViewModel()
        setupObservers(view)
        TestIdlingResource.increment()

        return view
    }

    private fun setupObservers(view: View) {
        viewModel.loader.observe(this as LifecycleOwner) { loading ->
            view.findViewById<ProgressBar>(R.id.loader).visibility = when (loading) {
                true -> View.VISIBLE
                else -> View.GONE
            }
        }

        viewModel.playlists.observe(this as LifecycleOwner) { playlists ->
            if (playlists.getOrNull() != null) {
                setupList(view.findViewById(R.id.playlists_list), playlists.getOrNull()!!)
            }
        }
    }

    private fun setupList(
        view: RecyclerView,
        playlists: List<Playlist>
    ) {
        with(view) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyPlaylistRecyclerViewAdapter(playlists) { id ->
                val action =
                    PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistDetailFragment(id)
                findNavController().navigate(action)
            }
        }
        TestIdlingResource.decrement()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[PlaylistViewModel::class.java]
    }
}