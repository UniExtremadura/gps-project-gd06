package com.spotify.quavergd06.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spotify.quavergd06.R
import com.spotify.quavergd06.data.model.StatsItem


class ArtistInfoFragment : Fragment() {

    private var statsItem: StatsItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ArtistInfoFragment", "onCreate")

        arguments?.let {
            statsItem = it.getSerializable("statsItem") as? StatsItem
        }

        Log.d("ArtistInfoFragment", "statsItem: $statsItem")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artist_info, container, false)
    }

    companion object {
        fun newInstance(statsItem: StatsItem) = ArtistInfoFragment().apply {
            arguments = Bundle().apply {
                putSerializable("statsItem", statsItem)
            }
        }
    }
}