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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("StatsFragment", "setUpViewPager")
        setUpViewPager()
    }

    fun setUpViewPager() {
        val adapter = PersonalTopGlobalViewPager(childFragmentManager,
            { i: Int ,fragment: Fragment -> findNavController().navigate(i, fragment.arguments)},
            { i: Int -> findNavController().navigate(i)}
        )
        binding.topItemViewPager.adapter = adapter
        binding.personalGlobalTabLayout.setupWithViewPager(binding.topItemViewPager)
    }

    private fun onClick(statsItem: StatsItem){
        if (statsItem.artist == null) {
            findNavController().navigate(R.id.action_topItemViewPagerFragment_to_artistInfoFragment, ArtistInfoFragment.newInstance(statsItem).arguments)
        } else {
            findNavController().navigate(R.id.action_topItemViewPagerFragment_to_trackInfoFragment, TrackInfoFragment.newInstance(statsItem).arguments)
        }
    }

    private fun navigateToTopItemFragment(fragment: Fragment) {
        Log.d("StatsFragment", "navigateToTopItemViewPagerFragment")
        findNavController().navigate(R.id.action_statsFragment_to_topItemViewPagerFragment, fragment.arguments)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = StatsFragment()
    }
}