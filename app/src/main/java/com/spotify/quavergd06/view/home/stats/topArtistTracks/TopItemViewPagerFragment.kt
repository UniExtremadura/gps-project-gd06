package com.spotify.quavergd06.view.home.stats.topArtistTracks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.spotify.quavergd06.databinding.FragmentTopItemViewPagerBinding
import com.spotify.quavergd06.view.home.stats.details.TrackInfoFragmentArgs

class TopItemViewPagerFragment : Fragment() {
    private val TAG = "TopItemViewPagerFragment"

    private var _binding: FragmentTopItemViewPagerBinding? = null
    private val binding get() = _binding!!

    private val args: TopItemViewPagerFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopItemViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "setUpViewPager")

        val itemClass = args.itemClass
        itemClass?.let { setUpViewPager(it) }
        setUpViewPager(itemClass)
    }

    private fun setUpViewPager(itemClass: String?) {
        val adapter = TopItemGridPagerAdapter(childFragmentManager, itemClass!!)
        binding.topItemViewPager.adapter = adapter
        binding.topItemTabLayout.setupWithViewPager(binding.topItemViewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}