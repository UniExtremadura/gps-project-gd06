package com.spotify.quavergd06.view.home

import TopArtistGridPagerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.spotify.quavergd06.databinding.FragmentTopArtistsViewPagerBinding

class TopArtistsViewPagerFragment : Fragment() {
    private var _binding: FragmentTopArtistsViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopArtistsViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val adapter = TopArtistGridPagerAdapter(childFragmentManager)
        binding.topArtistViewPager.adapter = adapter
        binding.topArtistTabLayout.setupWithViewPager(binding.topArtistViewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}