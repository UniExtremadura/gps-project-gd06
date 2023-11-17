package com.spotify.quavergd06.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import com.spotify.quavergd06.R
import com.spotify.quavergd06.databinding.FragmentStatsBinding


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

        val buttonTopArtistsMore = _binding!!.moreTopArtists
        buttonTopArtistsMore.setOnClickListener {
            navigateToTopArtistFragment()
        }
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

    private fun navigateToTopArtistFragment() {
        findNavController().navigate(R.id.action_statsFragment_to_topArtistsViewPagerFragment)
    }

}