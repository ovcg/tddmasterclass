package com.example.tddmasterclass.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.tddmasterclass.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaylistDetailFragment : Fragment() {

    private lateinit var viewModel: PlaylistDetailsViewModel
    private lateinit var viewModelFactory: PlaylistDetailsViewModelFactory

    val args: PlaylistDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_playlist_detail, container, false)

        val id = args.playlistid

        viewModel = ViewModelProvider(this, viewModelFactory)[PlaylistDetailsViewModel::class.java]

        viewModel.getPlaylistDetails(id)

        observeLiveData(view)

        return view
    }

    private fun observeLiveData(view: View) {
        viewModel.playlistDetails.observe(this as LifecycleOwner) { playlistDetails ->
            if (playlistDetails.getOrNull() != null) {
                view.findViewById<TextView>(R.id.playlist_name).text =
                    playlistDetails.getOrNull()!!.name
                view.findViewById<TextView>(R.id.playlist_details).text =
                    playlistDetails.getOrNull()!!.details
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlaylistDetailFragment()
    }
}