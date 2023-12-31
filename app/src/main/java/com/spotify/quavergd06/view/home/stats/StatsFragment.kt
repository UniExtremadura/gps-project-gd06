package com.spotify.quavergd06.view.home.stats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.spotify.quavergd06.databinding.FragmentStatsBinding


class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!


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
        val adapter = PersonalTopGlobalPagerAdapter(childFragmentManager)
        binding.topItemViewPager.adapter = adapter
        binding.personalGlobalTabLayout.setupWithViewPager(binding.topItemViewPager)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}