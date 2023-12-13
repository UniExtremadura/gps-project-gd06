package com.spotify.quavergd06.view.home.stats

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.spotify.quavergd06.R
import com.spotify.quavergd06.api.setKey
import com.spotify.quavergd06.data.fetchables.TopGlobalFetchable
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.databinding.FragmentRecyclerViewBinding
import com.spotify.quavergd06.view.home.stats.history.HistoryListAdapter
import com.spotify.quavergd06.view.home.stats.topArtistTracks.TrackInfoFragment
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class GlobalTopFragment(
    private val navigate: (Int, Fragment) -> Unit
): Fragment() {
    private var _binding: FragmentRecyclerViewBinding? = null
    private val binding get() = _binding!!

    private var items: List<StatsItem> = emptyList()
    private lateinit var adapter : HistoryListAdapter

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

        lifecycleScope.launch {
            try {
                setKey(obtenerSpotifyApiKey(requireContext())!!)
                items = TopGlobalFetchable().fetch()
                adapter.updateData(items)
            } catch (e: Exception) {
                Log.d("GlobalTopFragment", "Error: ${e.message}")
            }
        }

    }


    private fun setUpRecyclerView() {
        adapter = HistoryListAdapter(
            statsItems = items,
            context = this.context,
            onClick = {statsItem -> navigate(R.id.action_statsFragment_to_trackInfoFragment, TrackInfoFragment.newInstance(statsItem)) }
        )
        with(binding) {
            topItemRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            topItemRecyclerView.adapter = adapter

        }

        Log.d("GlobalTopFragment", "setUpRecyclerView")
    }

    private fun obtenerSpotifyApiKey(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("access_token", null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        lifecycleScope.cancel()
    }


    companion object {
        fun newInstance() = GlobalTopFragment { id, fragment ->
            fragment.findNavController().navigate(id)
        }

    }
}