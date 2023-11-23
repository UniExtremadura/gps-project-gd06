package com.spotify.quavergd06.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.spotify.quavergd06.R
import com.spotify.quavergd06.data.fetchables.ArtistFetchable
import com.spotify.quavergd06.data.fetchables.HistoryFetchable
import com.spotify.quavergd06.data.fetchables.TrackFetchable
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.databinding.FragmentPersonalStatsBinding
import com.spotify.quavergd06.databinding.FragmentStatsBinding

class PersonalStatsFragment(
    private val navigate: (Int, Fragment) -> Unit,
    private val navigateNoArgs: (Int) -> Unit
) : Fragment() {
    private var _binding: FragmentPersonalStatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPersonalStatsBinding.inflate(inflater, container, false)

        setButtons()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadFragment(R.id.fragmentTopGenres, TopGenresFragment())

        loadFragment(R.id.fragmentTopArtists, PreviewTopFragment(ArtistFetchable()) { statsItem ->
            navigate(
                R.id.action_statsFragment_to_artistInfoFragment,
                ArtistInfoFragment.newInstance(statsItem)
            )
        })

        loadFragment(R.id.fragmentTopTracks, PreviewTopFragment(TrackFetchable()) { statsItem ->
            navigate(
                R.id.action_statsFragment_to_trackInfoFragment,
                TrackInfoFragment.newInstance(statsItem)
            )
        })

        loadFragment(R.id.fragmentHistory, PreviewTopFragment(HistoryFetchable()) { statsItem ->
            navigate(
                R.id.action_statsFragment_to_trackInfoFragment,
                TrackInfoFragment.newInstance(statsItem)
            )
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
            navigate(
                R.id.action_statsFragment_to_topItemViewPagerFragment,
                TopItemViewPagerFragment.newInstance("short_term", ArtistFetchable())
            )
        }

        val buttonTopTracksMore = binding.moreTopTracks
        buttonTopTracksMore.setOnClickListener { //findNavController().navigate(R.id.action_statsFragment_to_topItemViewPagerFragment, TopItemViewPagerFragment.newInstance("short_term", TrackFetchable()).arguments)
            navigate(
                R.id.action_statsFragment_to_topItemViewPagerFragment,
                TopItemViewPagerFragment.newInstance("short_term", TrackFetchable())
            )
        }

        val buttonTopHistory = binding.moreHistory
        buttonTopHistory.setOnClickListener {
            navigateNoArgs(R.id.action_statsFragment_to_historyListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(navigate: (Int, Fragment) -> Unit, navigateNoArgs: (Int) -> Unit) =
            PersonalStatsFragment(navigate, navigateNoArgs)

        fun newInstance() = PersonalStatsFragment(
            { id, fragment ->
                fragment.findNavController().navigate(id)
            },
            { _ -> }
        )

    }

}