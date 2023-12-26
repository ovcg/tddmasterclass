package com.example.tddmasterclass.playlist

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.tddmasterclass.R

import com.example.tddmasterclass.databinding.FragmentPlaylistBinding

class MyPlaylistRecyclerViewAdapter(
    private val values: List<Playlist>,
    private val listener: (String) -> Unit
) : RecyclerView.Adapter<MyPlaylistRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentPlaylistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.playlistName.text = item.name
        holder.playlistCategory.text = item.category
        holder.playlistImage.setImageResource(item.image)
        holder.playlistItemRoot.setOnClickListener {
            listener.invoke(item.id)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val playlistName: TextView = binding.playlistName
        val playlistCategory: TextView = binding.playlistCategory
        val playlistImage: ImageView = binding.playlistImage
        val playlistItemRoot: LinearLayout = binding.playlistItemRoot
    }
}