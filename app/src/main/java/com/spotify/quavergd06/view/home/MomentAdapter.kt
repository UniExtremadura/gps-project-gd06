package com.spotify.quavergd06.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spotify.quavergd06.model.Moment
import com.spotify.quavergd06.databinding.FragmentMomentItemBinding
class MomentAdapter(
    private var moments: List<Moment>,
    private val onClick: (moment:Moment) -> Unit
) : RecyclerView.Adapter<MomentAdapter.ShowViewHolder>() {

    class ShowViewHolder(
        private val binding: FragmentMomentItemBinding,
        private val onClick: (moment: Moment) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(moment: Moment, totalItems: Int) {
            with(binding) {
                momentTitle.text = moment.title
                momentImg.setImageResource(moment.image)
                clItem.setOnClickListener {
                    onClick(moment)
                }
            }
        }
    }

        fun swap(newMoments: List<Moment>) {
        moments = newMoments
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val binding =
            FragmentMomentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowViewHolder(binding, onClick)
    }

    override fun getItemCount() = moments.size

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        holder.bind(moments[position], moments.size)
    }



}