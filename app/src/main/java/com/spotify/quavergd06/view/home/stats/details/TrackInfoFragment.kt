package com.spotify.quavergd06.view.home.stats.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.spotify.quavergd06.R
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.databinding.FragmentTrackInfoBinding
import com.spotify.quavergd06.view.home.HomeViewModel
import com.squareup.picasso.Picasso


class TrackInfoFragment : Fragment() {
    private val TAG = "TrackInfoFragment"

    private var _binding: FragmentTrackInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TrackInfoViewModel by viewModels { TrackInfoViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()

    private val args:  TrackInfoFragmentArgs by navArgs()

    private lateinit var _artist: StatsItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrackInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
        val track = args.statsItem

        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.user = user
        }

        viewModel.track = track

        // Show a Toast whenever the [toast] is updated a non-null value
        viewModel.toast.observe(viewLifecycleOwner) { text ->
            text?.let {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }

        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.trackDetail.observe(viewLifecycleOwner) { track ->
            track?.let{
                _artist = StatsItem(id = track.artistId, name = track.artistName)
                setButtonListener()
                setupUI(track)
            }
        }
    }

    private fun setupUI(statsItem: StatsItem) {
        with(binding) {
            artistName.text = getString(R.string.artist, statsItem.artistName)

            trackName.text = getString(R.string.track_title, statsItem.name.toString())
            albumName.text = getString(R.string.album_name, statsItem.album)

            Picasso.get().load(statsItem.imageUrls?.get(0)).into(albumImage)
        }
    }

    private fun setButtonListener() {
        binding.artistName.setOnClickListener {
            homeViewModel.navigateFromTrackDetailToArtistDetail(_artist!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}