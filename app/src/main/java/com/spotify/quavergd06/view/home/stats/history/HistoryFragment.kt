package com.spotify.quavergd06.view.home.stats.history

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.spotify.quavergd06.R
import com.spotify.quavergd06.api.setKey
import com.spotify.quavergd06.data.fetchables.HistoryFetchable
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.data.model.Track
import com.spotify.quavergd06.data.toStatsItem
import com.spotify.quavergd06.databinding.FragmentRecyclerViewBinding
import com.spotify.quavergd06.view.home.HomeViewModel
import com.spotify.quavergd06.view.home.stats.GlobalTopViewModel
import com.spotify.quavergd06.view.home.stats.topArtistTracks.TrackInfoFragment
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class HistoryFragment() : Fragment() {
    private var _binding: FragmentRecyclerViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : ListTracksAdapter
    private val viewModel: HistoryViewModel by viewModels { HistoryViewModel.Factory }
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

    private fun subscribeUi(adapter: ListTracksAdapter) {
        viewModel.tracks.observe(viewLifecycleOwner) { tracks ->
            adapter.updateData(tracks.map(Track::toStatsItem))
        }
    }

    private fun setUpRecyclerView() {
        adapter = ListTracksAdapter(
            statsItems = emptyList(),
            context = this.context,
            onClick = { track -> homeViewModel.navigateFromHistoryToTrackDetail(track) }
        )
        with(binding) {
            topItemRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            topItemRecyclerView.adapter = adapter

        }

        Log.d("HistoryFragment", "setUpRecyclerView")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}