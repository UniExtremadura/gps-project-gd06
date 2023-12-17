package com.spotify.quavergd06.view.home.stats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.spotify.quavergd06.R
import com.spotify.quavergd06.databinding.FragmentPersonalStatsBinding
import com.spotify.quavergd06.view.home.HomeViewModel
import com.spotify.quavergd06.view.home.stats.preview.ArtistPreviewFragment
import com.spotify.quavergd06.view.home.stats.preview.HistoryPreviewFragment
import com.spotify.quavergd06.view.home.stats.preview.TrackPreviewFragment
import com.spotify.quavergd06.view.home.stats.topGenres.TopGenresFragment

class PersonalStatsFragment() : Fragment() {
    private var _binding: FragmentPersonalStatsBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by activityViewModels()

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
        loadFragment(R.id.fragmentTopArtists, ArtistPreviewFragment())
        loadFragment(R.id.fragmentTopTracks, TrackPreviewFragment())
        loadFragment(R.id.fragmentHistory, HistoryPreviewFragment())
    }

    private fun loadFragment(containerId: Int, fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }

    private fun setButtons() {
        val buttonTopArtistsMore = binding.moreTopArtists
        buttonTopArtistsMore.setOnClickListener {
//            homeViewModel.navigateFromStatsToTopItemViewPagerFragment()
        }

        val buttonTopTracksMore = binding.moreTopTracks
        buttonTopTracksMore.setOnClickListener { //findNavController().navigate(R.id.action_statsFragment_to_topItemViewPagerFragment, TopItemViewPagerFragment.newInstance("short_term", TrackFetchable()).arguments)
//            navigate(
//                R.id.action_statsFragment_to_topItemViewPagerFragment,
//                TopItemViewPagerFragment.newInstance("short_term", TrackFetchable())
//            )
        }

        val buttonTopHistory = binding.moreHistory
        buttonTopHistory.setOnClickListener {
//            navigateNoArgs(R.id.action_statsFragment_to_historyFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}