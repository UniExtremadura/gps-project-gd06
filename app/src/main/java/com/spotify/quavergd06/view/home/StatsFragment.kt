package com.spotify.quavergd06.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import com.spotify.quavergd06.R
import com.spotify.quavergd06.data.fetchables.ArtistFetchable
import com.spotify.quavergd06.data.fetchables.Fetchable
import com.spotify.quavergd06.data.fetchables.HistoryFetchable
import com.spotify.quavergd06.data.fetchables.TrackFetchable
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.databinding.FragmentStatsBinding


class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStatsBinding.inflate(inflater, container, false)

        setButtons()

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadFragment(R.id.fragmentTopGenres, TopGenresFragment())

        loadFragment(R.id.fragmentTopArtists, PreviewTopFragment(ArtistFetchable()) { statsItem ->
            findNavController().navigate(R.id.action_statsFragment_to_artistInfoFragment, ArtistInfoFragment.newInstance(statsItem).arguments)
        })

        loadFragment(R.id.fragmentTopTracks, PreviewTopFragment(TrackFetchable()) { statsItem ->
            findNavController().navigate(R.id.action_statsFragment_to_trackInfoFragment, TrackInfoFragment.newInstance(statsItem).arguments)
        })

        loadFragment(R.id.fragmentHistory, PreviewTopFragment(HistoryFetchable()) { statsItem ->
            findNavController().navigate(R.id.action_statsFragment_to_trackInfoFragment, TrackInfoFragment.newInstance(statsItem).arguments)
        })
    }

    private fun loadFragment(containerId: Int, fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }

    private fun setButtons() {
        val buttonTopArtistsMore = binding.moreTopArtists
        buttonTopArtistsMore.setOnClickListener {
            val fetchable = ArtistFetchable()
            Log.d("StatsFragment", "ArtistFetchable instance: $fetchable")
            navigateToTopItemFragment(TopItemViewPagerFragment.newInstance("short_term", fetchable))
        }

        val buttonTopTracksMore = binding.moreTopTracks
        buttonTopTracksMore.setOnClickListener {
            val fetchable = TrackFetchable()
            Log.d("StatsFragment", "TrackFetchable instance: $fetchable")
            navigateToTopItemFragment(TopItemViewPagerFragment.newInstance("short_term", fetchable))
        }
    }

    private fun navigateToTopItemFragment(fragment: Fragment) {
        Log.d("StatsFragment", "navigateToTopItemViewPagerFragment")
        findNavController().navigate(R.id.action_statsFragment_to_topItemViewPagerFragment, fragment.arguments)
    }

}