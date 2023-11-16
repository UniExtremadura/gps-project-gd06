package com.spotify.quavergd06.view.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spotify.quavergd06.data.api.ArtistItem
import com.spotify.quavergd06.data.model.Artist
import com.spotify.quavergd06.databinding.FragmentTopItemBinding
import com.squareup.picasso.Picasso

class ArtistAdapter(
    private var artists: List<Artist>,
    private val context: Context?
) : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {
    class ArtistViewHolder(
        private val binding: FragmentTopItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Artist, totalItems: Int) {
            with(binding) {
                topItemTitle.text = artist.name
                Picasso.get().load(artist.imageUrls?.get(2)).into(topItemImg)
                Log.d("ArtistAdapter", "Artist name: ${artist.name}")
                Log.d("ArtistAdapter", "Artist image: ${artist.imageUrls?.get(2)}")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val binding = FragmentTopItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(artists[position], artists.size)
    }

    fun updateData(artists: List<Artist>) {
        this.artists = artists
        notifyDataSetChanged()
    }

    override fun getItemCount() = artists.size


}