package com.spotify.quavergd06.view.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spotify.quavergd06.databinding.FragmentTopGenresAdapterBinding

class GenresAdapter(
    private var genres: List<String>,
    private val context: Context?
) : RecyclerView.Adapter<GenresAdapter.GenresViewHolder>() {

    class GenresViewHolder(
        private val binding: FragmentTopGenresAdapterBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: String, totalItems: Int) {
            with(binding) {
                topGenresTitle.text = genre

                Log.d("GenresAdapter", "Genre: ${genre}")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val binding =
            FragmentTopGenresAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenresViewHolder(binding)
    }


    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        holder.bind(genres[position], genres.size)
    }

    fun updateData(genres: List<String>) {
        this.genres = genres
        notifyDataSetChanged()
    }

    override fun getItemCount() = genres.size


}