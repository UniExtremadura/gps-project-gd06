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
import com.spotify.quavergd06.databinding.FragmentArtistInfoBinding
import com.spotify.quavergd06.view.home.HomeViewModel
import com.squareup.picasso.Picasso


class ArtistInfoFragment : Fragment() {
    private val TAG = "ArtistInfoFragment"

    private var _binding: FragmentArtistInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ArtistInfoViewModel by viewModels { ArtistInfoViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()

    private val args:  TrackInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtistInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        val artist = args.statsItem

        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.user = user
        }

        viewModel.artist = artist

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
        viewModel.artistDetail.observe(viewLifecycleOwner) { artist ->
            artist?.let{
                setupUI(artist)
            }
        }
    }

    private fun setupUI(statsItem: StatsItem) {
        with(binding) {
            artistName.text = getString(R.string.artist, statsItem.name)
            artistPopularity.text = getString(R.string.popularity, statsItem.popularity.toString())
            artistGenres.text = getString(R.string.genres, statsItem.genres?.joinToString())
            Log.d(TAG, "imageUrls: ${statsItem.imageUrls}")
            Picasso.get().load(statsItem.imageUrls?.get(0)).into(artistImage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}