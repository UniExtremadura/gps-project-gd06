package com.spotify.quavergd06.view.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spotify.quavergd06.R
import com.spotify.quavergd06.api.APIException
import com.spotify.quavergd06.api.getNetworkService
import com.spotify.quavergd06.api.setKey
import com.spotify.quavergd06.data.api.ArtistItem
import com.spotify.quavergd06.data.api.TrackItem
import com.spotify.quavergd06.data.model.Artist
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.data.model.Track
import com.spotify.quavergd06.data.toArtist
import com.spotify.quavergd06.data.toStatsItem
import com.spotify.quavergd06.data.toTrack
import com.spotify.quavergd06.databinding.FragmentTopPreviewBinding
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


interface Fetchable {
    suspend fun fetch(): List<StatsItem>
}

class ArtistFetchable : Fetchable {
    override suspend fun fetch(): List<StatsItem> {
        val apiArtists = getNetworkService().loadTopArtists("medium_term").body()?.artistItems ?: emptyList()
        return apiArtists.map(ArtistItem::toArtist).map(Artist::toStatsItem)
    }
}

class TrackFetchable : Fetchable {
    override suspend fun fetch(): List<StatsItem> {
        val apiTracks = getNetworkService().loadTopTracks("medium_term").body()?.trackItems ?: emptyList()
        return apiTracks.map(TrackItem::toTrack).map(Track::toStatsItem)
    }
}


class PreviewTopFragment(private val fetchable: Fetchable) : Fragment() {
    private var _binding: FragmentTopPreviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: StatsItemAdapter
    private var items: List<StatsItem> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopPreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        // Realizar una búsqueda de artistas en Spotify
        lifecycleScope.launch {
            try {
                setKey(obtenerSpotifyApiKey(requireContext())!!)
                items = fetchable.fetch()
                adapter.updateData(items)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun setUpRecyclerView() {
        adapter = StatsItemAdapter(
            statsItems = items,
            context = this.context
        )
        with(binding) {
            recyclerViewTopPreview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,  false)
            recyclerViewTopPreview.adapter = adapter
        }
        android.util.Log.d("ArtistFragment", "setUpRecyclerView")
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
}
