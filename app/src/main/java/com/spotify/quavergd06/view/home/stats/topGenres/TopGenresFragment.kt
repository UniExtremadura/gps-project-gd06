package com.spotify.quavergd06.view.home.stats.topGenres

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.spotify.quavergd06.api.getNetworkService
import com.spotify.quavergd06.api.setKey
import com.spotify.quavergd06.data.api.ArtistItem
import com.spotify.quavergd06.data.model.Artist
import com.spotify.quavergd06.data.toArtist
import com.spotify.quavergd06.data.toStatsItem
import com.spotify.quavergd06.databinding.FragmentTopGenresBinding
import com.spotify.quavergd06.view.home.HomeViewModel
import com.spotify.quavergd06.view.home.stats.StatsItemAdapter
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class TopGenresFragment : Fragment() {
    private val TAG = "TopGenresFragment"

    private var _binding: FragmentTopGenresBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GenresAdapter

    private val viewModel: TopGenresViewModel by viewModels { TopGenresViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopGenresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.user = user
        }

        subscribeUI(adapter)
    }

    private fun subscribeUI(adapter: GenresAdapter) {
        viewModel.genres?.observe(viewLifecycleOwner) { genres ->
            adapter.updateData(genres)
        }
    }

    private fun setUpRecyclerView() {
        adapter = GenresAdapter(
            genres = emptyList(),
            context = this.context
        )
        with(binding) {
            topGenresRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,  false)
            topGenresRecyclerView.adapter = adapter
        }
        Log.d(TAG, "RecyclerView")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        lifecycleScope.cancel()
    }

    companion object {
        fun newInstance() = TopGenresFragment()
    }

}