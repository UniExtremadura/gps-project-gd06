package com.spotify.quavergd06.view.home.stats.preview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.spotify.quavergd06.data.model.Artist
import com.spotify.quavergd06.data.model.Track
import com.spotify.quavergd06.data.toStatsItem
import com.spotify.quavergd06.databinding.FragmentTopPreviewBinding
import com.spotify.quavergd06.view.home.HomeViewModel
import com.spotify.quavergd06.view.home.stats.StatsItemAdapter

class HistoryPreviewFragment() : Fragment() {
    private val TAG = "HistoryPreviewFragment"

    private var _binding: FragmentTopPreviewBinding? = null
    private val binding get() = _binding!!

    private var adapter: StatsItemAdapter = StatsItemAdapter(emptyList(), {}, null)

    private val viewModel: HistoryPreviewViewModel by viewModels { HistoryPreviewViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopPreviewBinding.inflate(inflater, container, false)
        Log.d(TAG, "onCreateView")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        Log.d(TAG, "onViewCreated")

        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.user = user
        }

        subscribeUI(adapter)
    }

    private fun subscribeUI(adapter: StatsItemAdapter) {
        viewModel.tracks.observe(viewLifecycleOwner) { tracks ->
            adapter.updateData(tracks.map(Track::toStatsItem))
        }
    }

    private fun setUpRecyclerView() {
        adapter = StatsItemAdapter(
            statsItems = emptyList(),
            context = this.context,
            onClick = {statsItem ->
                homeViewModel.navigateFromStatsToTrackDetail(statsItem)
            }
        )
        with(binding) {
            recyclerViewTopPreview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,  false)
            recyclerViewTopPreview.adapter = adapter
        }
        Log.d(TAG, "setUpRecyclerView")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
