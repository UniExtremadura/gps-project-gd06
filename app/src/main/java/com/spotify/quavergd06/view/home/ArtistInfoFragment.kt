package com.spotify.quavergd06.view.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.spotify.quavergd06.R
import com.spotify.quavergd06.api.setKey
import com.spotify.quavergd06.data.fetchables.ArtistFetchable
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.data.toStatsItem
import com.spotify.quavergd06.databinding.FragmentArtistInfoBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch


class ArtistInfoFragment : Fragment() {
    private var _binding: FragmentArtistInfoBinding? = null
    private val binding get() = _binding!!


    private lateinit var statsItem: StatsItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ArtistInfoFragment", "onCreate")

        arguments?.let {
            statsItem = it.getSerializable("statsItem") as StatsItem
        }

        Log.d("ArtistInfoFragment", "statsItem: $statsItem")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtistInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI(statsItem!!)
        Log.d("ArtistInfoFragment", "onViewCreated")
    }

    private fun setupUI(statsItem: StatsItem) {
        with(binding) {
            artistName.text = getString(R.string.artist, statsItem.name)
            artistPopularity.text = getString(R.string.popularity, statsItem.popularity.toString())
            artistGenres.text = getString(R.string.genres, statsItem.genres?.joinToString())
            Log.d("ArtistInfoFragment", "imageUrls: ${statsItem.imageUrls}")
            Picasso.get().load(statsItem.imageUrls?.get(0)).into(artistImage)
        }
    }

    companion object {
        fun newInstance(statsItem: StatsItem) = ArtistInfoFragment().apply {
            arguments = Bundle().apply {
                putSerializable("statsItem", statsItem)
            }
        }
    }
}