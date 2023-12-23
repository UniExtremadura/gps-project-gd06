package com.spotify.quavergd06.view.home.stats

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.databinding.FragmentTopItemBinding
import com.squareup.picasso.Picasso


class StatsItemAdapter(
    private var statsItems: List<StatsItem>,
    private val onClick: (statsItem: StatsItem) -> Unit,
    private val context: Context?
) : RecyclerView.Adapter<StatsItemAdapter.StatsItemViewHolder>() {

    class StatsItemViewHolder(
        private val binding: FragmentTopItemBinding,
        private val onClick: (statsItem: StatsItem) -> Unit,

        ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StatsItem, totalItems: Int) {
            with(binding) {
                topItemTitle.text = item.name
                Picasso.get().load(item.imageUrls?.get(2)).into(topItemImg)

                //Log de nombre y log de imagenes
                Log.d("com.spotify.quavergd06.view.home.stats.StatsItemAdapter", "Name: ${item.name}")
                Log.d("com.spotify.quavergd06.view.home.stats.StatsItemAdapter", "Images: ${item.imageUrls}")

                fragmentItem.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsItemViewHolder {
        val binding =
            FragmentTopItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StatsItemViewHolder(binding, onClick)
    }


    override fun onBindViewHolder(holder: StatsItemViewHolder, position: Int) {
        holder.bind(statsItems[position], statsItems.size)
    }

    fun updateData(statsItems: List<StatsItem>) {
        this.statsItems = statsItems
        notifyDataSetChanged()
    }

    override fun getItemCount() = statsItems.size

}