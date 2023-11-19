package com.spotify.quavergd06.view.home

import TopItemGridPagerAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.spotify.quavergd06.R
import com.spotify.quavergd06.data.fetchables.Fetchable
import com.spotify.quavergd06.data.model.Artist
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.databinding.FragmentTopItemViewPagerBinding

class TopItemViewPagerFragment : Fragment() {
    private var _binding: FragmentTopItemViewPagerBinding? = null
    private val binding get() = _binding!!

    private var term: String? = null
    private var fetchable: Fetchable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("TopItemViewPagerFragment", "onCreate")

        arguments?.let {
            term = it.getString("term")
            fetchable = it.getSerializable("fetchable") as? Fetchable
        }

        Log.d("TopItemViewPagerFragment", "term: $term, fetchable: $fetchable")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopItemViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TopItemViewPagerFragment", "setUpViewPager")
        setUpViewPager()
    }

    private fun setUpViewPager() {
        fetchable?.let { notNullFetchable ->
            val adapter = TopItemGridPagerAdapter(childFragmentManager, notNullFetchable) { statsItem ->
                onPreviewItemClick(
                    statsItem
                )
            }
            binding.topItemViewPager.adapter = adapter
            binding.topItemTabLayout.setupWithViewPager(binding.topItemViewPager)
        } ?: Log.e("TopItemViewPagerFragment", "fetchable is null")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Crea una función que devuelva un callback de navegación a ArtistInfoFragment o un TrackInfoFragment en función del tipo de StatsItem
    private fun onPreviewItemClick(statsItem: StatsItem){
        if (statsItem.genres != null && statsItem.genres!!.size >  0) {
            // If statsItem has a genre, navigate to TopArtistsFragment
            findNavController().navigate(R.id.action_topItemViewPagerFragment_to_artistInfoFragment, ArtistInfoFragment.newInstance(statsItem).arguments)
        } else {
            // If statsItem doesn't have a genre, navigate to TopTracksFragment
            findNavController().navigate(R.id.action_topItemViewPagerFragment_to_trackInfoFragment, TrackInfoFragment.newInstance(statsItem).arguments)
        }
    }

    companion object {
        fun newInstance(term: String, fetchable: Fetchable) =
            TopItemViewPagerFragment().apply {
                arguments = Bundle().apply {
                    putString("term", term)
                    putSerializable("fetchable", fetchable)
                }
            }
    }

}