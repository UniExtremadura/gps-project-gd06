package com.spotify.quavergd06.view.home.stats.topArtistTracks.topArtist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.spotify.quavergd06.data.model.Artist
import com.spotify.quavergd06.data.toStatsItem

import com.spotify.quavergd06.databinding.FragmentRecyclerViewBinding
import com.spotify.quavergd06.view.home.HomeViewModel
import com.spotify.quavergd06.view.home.stats.StatsItemAdapter


class TopArtistGridFragment() : Fragment() {
    private val TAG = "TopArtistGridFragment"

    private var _binding: FragmentRecyclerViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : StatsItemAdapter

    private val viewModel: TopArtistGridViewModel by viewModels { TopArtistGridViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.user = user
        }

        subscribeUi(adapter)
    }

    private fun subscribeUi(adapter: StatsItemAdapter) {
        viewModel.artist.observe(viewLifecycleOwner) { artist ->
            adapter.updateData(artist.map(Artist::toStatsItem))
        }
    }

    private fun setUpRecyclerView() {
        adapter = StatsItemAdapter(
            statsItems = emptyList(),
            context = this.context,
            onClick = {statsItem -> homeViewModel.navigateFromTopGridToArtistDetail(statsItem) }
        )
        with(binding) {
            topItemRecyclerView.layoutManager = GridLayoutManager(context, 2)
            topItemRecyclerView.adapter = adapter
        }
        Log.d(TAG, "setUpRecyclerView")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
