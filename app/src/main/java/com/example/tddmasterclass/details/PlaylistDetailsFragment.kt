package com.example.tddmasterclass.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.tddmasterclass.R
import com.example.tddmasterclass.utils.TestIdlingResource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistDetailFragment : Fragment() {

    private lateinit var viewModel: PlaylistDetailsViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistDetailsViewModelFactory

    private val args: PlaylistDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_playlist_detail, container, false)

        val id = args.playlistid

        viewModel = ViewModelProvider(this, viewModelFactory)[PlaylistDetailsViewModel::class.java]

        observePlaylistDetails(view)

        observeLoader(view)

        TestIdlingResource.increment()

        viewModel.getPlaylistDetails(id)

        return view
    }

    private fun observePlaylistDetails(view: View) {
        viewModel.playlistDetails.observe(this as LifecycleOwner) { playlistDetails ->
            if (playlistDetails.getOrNull() != null) {
                view.findViewById<TextView>(R.id.playlist_name).text =
                    playlistDetails.getOrNull()!!.name
                view.findViewById<TextView>(R.id.playlist_details).text =
                    playlistDetails.getOrNull()!!.details
            } else {
                Snackbar.make(
                    view.findViewById(R.id.playlist_details_root),
                    R.string.generic_error,
                    Snackbar.LENGTH_LONG
                ).show()
            }
            TestIdlingResource.decrement()
        }
    }

    private fun observeLoader(view: View) {
        viewModel.loader.observe(this as LifecycleOwner) { load ->
            view.findViewById<ProgressBar>(R.id.details_loader).visibility = if (load) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlaylistDetailFragment()
    }
}