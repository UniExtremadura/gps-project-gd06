package com.spotify.quavergd06.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import com.spotify.quavergd06.R
import com.spotify.quavergd06.databinding.FragmentStatsBinding
import kotlin.math.log


class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStatsBinding.inflate(inflater, container, false)

        setButtons()

        return _binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadFragment(R.id.fragmentTopArtists, PreviewTopFragment())
    }

    private fun loadFragment(containerId: Int, fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }

    private fun setButtons(){
        val buttonTopArtistsMore = _binding!!.moreTopArtists
        buttonTopArtistsMore.setOnClickListener {
            navigateToTopArtistFragment()
        }

        val buttonTopTracksMore = _binding!!.moreTopTracks
        buttonTopTracksMore.setOnClickListener {
            navigateToTopTracksFragment()
        }
    }

    private fun navigateToTopArtistFragment() {
        Log.d("StatsFragment", "navigateToTopArtistFragment")
        findNavController().navigate(R.id.action_statsFragment_to_topArtistsViewPagerFragment)
    }

    private fun navigateToTopTracksFragment() {
        Log.d("StatsFragment", "navigateToTopTracksFragment")
        findNavController().navigate(R.id.action_statsFragment_to_topTracksViewPagerFragment)
    }

}