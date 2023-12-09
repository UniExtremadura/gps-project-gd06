package com.spotify.quavergd06.view.home.stats.topG

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.spotify.quavergd06.api.getNetworkService
import com.spotify.quavergd06.api.setKey
import com.spotify.quavergd06.data.api.ArtistItem
import com.spotify.quavergd06.data.model.Artist
import com.spotify.quavergd06.data.toArtist
import com.spotify.quavergd06.databinding.FragmentTopGenresBinding
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class TopGenresFragment : Fragment() {
    private var _binding: FragmentTopGenresBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GenresAdapter
    private var artists = emptyList<Artist>()
    private var genres = emptyList<String>()
    private var topGenres = emptyList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTopGenresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        // Realizar una búsqueda de artistas en Spotify
        lifecycleScope.launch {
            try {
                setKey(obtenerSpotifyApiKey(requireContext())!!)
                artists = fetch("long_term")
                genres = getGenresFromArtistList(artists)

                android.util.Log.d("TopGenresFragment", "Genres: $genres")

                val topGenres = getTopGenres(genres)

                android.util.Log.d("TopGenresFragment", "Top Genres: $topGenres")

                adapter.updateData(topGenres)
            } catch (e: Exception) {
                Log.d("TopGenresFragment", "Error: ${e.message}")
            }
        }
    }

    suspend fun fetch(timePeriod: String): List<Artist> {
        val apiArtists = getNetworkService().loadTopArtists(timePeriod).body()?.artistItems ?: emptyList()
        return apiArtists.map(ArtistItem::toArtist)
    }

    private fun setUpRecyclerView() {
        adapter = GenresAdapter(
            genres = genres,
            context = this.context
        )
        with(binding) {
            topGenresRecyclerView.layoutManager = LinearLayoutManager(context, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,  false)
            topGenresRecyclerView.adapter = adapter
        }
        Log.d("TopGenresFragment", "setUpRecyclerView")
    }

//    private fun getGenresFromArtistList(artists: List<Artist>): List<String> {
//        return artists.flatMap { it.genres }
//    }
//
    //Refactoriza getGenresFromArtistList para que se adecúe al cambio hecho con el StringListWrapper
    private fun getGenresFromArtistList(artists: List<Artist>): List<String> {
        return artists.flatMap { it.genres.list }
    }

    private fun getTopGenres(genres: List<String>): List<String> {
        return genres.groupingBy { it }.eachCount().toList().sortedByDescending { (_, value) -> value }.map { (key, _) -> key }
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
        fun newInstance() = TopGenresFragment()
    }

}