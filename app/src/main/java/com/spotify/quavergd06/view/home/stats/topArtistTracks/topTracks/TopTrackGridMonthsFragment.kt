package com.spotify.quavergd06.view.home.stats.topArtistTracks.topTracks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.spotify.quavergd06.data.model.Track
import com.spotify.quavergd06.data.toStatsItem
import com.spotify.quavergd06.databinding.FragmentRecyclerViewBinding
import com.spotify.quavergd06.view.home.HomeViewModel
import com.spotify.quavergd06.view.home.stats.StatsItemAdapter
import com.spotify.quavergd06.view.home.stats.preview.TopTrackGridMonthViewModel
import com.spotify.quavergd06.view.home.stats.preview.TopTrackGridViewModel

class TopTrackGridMonthsFragment : Fragment() {
    private val TAG = "TopTrackGridMonthsFragment"

    private var _binding: FragmentRecyclerViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : StatsItemAdapter

    private val viewModel: TopTrackGridMonthViewModel by viewModels { TopTrackGridMonthViewModel.Factory }
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
        viewModel.tracks.observe(viewLifecycleOwner) { tracks ->
            adapter.updateData(tracks.map(Track::toStatsItem))
        }
    }

    private fun setUpRecyclerView() {
        adapter = StatsItemAdapter(
            statsItems = emptyList(),
            context = this.context,
            onClick = {statsItem -> homeViewModel.navigateFromTopGridToTrackDetail(statsItem) }
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