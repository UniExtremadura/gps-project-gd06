package com.spotify.quavergd06.view.home.stats.topArtistTracks

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.spotify.quavergd06.R
import com.spotify.quavergd06.api.getNetworkService
import com.spotify.quavergd06.api.setKey
import com.spotify.quavergd06.data.ArtistsRepository
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.data.toArtist
import com.spotify.quavergd06.data.toStatsItem
import com.spotify.quavergd06.database.QuaverDatabase
import com.spotify.quavergd06.databinding.FragmentTrackInfoBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch


class TrackInfoFragment : Fragment() {

    private var _binding: FragmentTrackInfoBinding? = null
    private val binding get() = _binding!!

    private var statsItem: StatsItem? = null

    private lateinit var artist: StatsItem

    private lateinit var db : QuaverDatabase
    private lateinit var artistsRepository : ArtistsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TrackInfoFragment", "onCreate")

        arguments?.let {
            statsItem = it.getSerializable("statsItem") as? StatsItem
        }

        Log.d("TrackInfoFragment", "statsItem: $statsItem")

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = QuaverDatabase.getInstance(context)!!
        artistsRepository = ArtistsRepository.getInstance(db.artistDAO(), getNetworkService())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrackInfoBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            try {
                artistsRepository.tryUpdateRecentArtistCache(statsItem?.artist?.artistId.toString())
                Log.d("TrackInfoFragment", "artist: $artist")
            }   catch (e: Exception) {
                Log.d("TrackInfoFragment", "Error: ${e.message}")
            }
            setButtonListener()
        }
        return binding.root
    }

    private fun setButtonListener() {
        binding.artistName.setOnClickListener{
            Log.d("TrackInfoFragment", "artistName clicked")
            findNavController().navigate(R.id.action_trackInfoFragment_to_artistInfoFragment, ArtistInfoFragment.newInstance(artist).arguments)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(statsItem!!)
        Log.d("TrackInfoFragment", "onViewCreated")
    }

    private fun setupUI(statsItem: StatsItem) {
        with(binding) {
            artistName.text = getString(R.string.artist, statsItem.artist?.name)

            trackName.text = getString(R.string.track_title, statsItem.name.toString())
            albumName.text = getString(R.string.album_name, statsItem.album)

            Picasso.get().load(statsItem.imageUrls?.get(0)).into(albumImage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun obtenerSpotifyApiKey(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("access_token", null)
    }

    companion object {
        fun newInstance(statsItem: StatsItem) = TrackInfoFragment().apply {
            arguments = Bundle().apply {
                putSerializable("statsItem", statsItem)
            }
        }

        fun newInstance() = TrackInfoFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }

}