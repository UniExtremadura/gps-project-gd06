package com.spotify.quavergd06.view.home.stats.topArtistTracks

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.spotify.quavergd06.view.home.stats.topArtistTracks.topArtist.TopArtistGridAllFragment
import com.spotify.quavergd06.view.home.stats.topArtistTracks.topArtist.TopArtistGridFragment
import com.spotify.quavergd06.view.home.stats.topArtistTracks.topArtist.TopArtistGridMonthViewModel
import com.spotify.quavergd06.view.home.stats.topArtistTracks.topArtist.TopArtistGridMonthsFragment
import com.spotify.quavergd06.view.home.stats.topArtistTracks.topTracks.TopTrackGridFragment
import com.spotify.quavergd06.view.home.stats.topArtistTracks.topTracks.TopTrackGridMonthsFragment
import com.spotify.quavergd06.view.home.stats.topArtistTracks.topTracks.TopTracksGridAllFragment

class TopItemGridPagerAdapter(fragmentManager: FragmentManager, private val itemClass: String) :
    FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        return when (itemClass) {
            "artist" -> {
                when (position) {
                    0 -> TopArtistGridFragment()
                    1 -> TopArtistGridMonthsFragment()
                    2 -> TopArtistGridAllFragment()
                    else -> TopArtistGridFragment()
                }
            }
            "track" -> {
                when (position) {
                    0 -> TopTrackGridFragment()
                    1 -> TopTrackGridMonthsFragment()
                    2 -> TopTracksGridAllFragment()
                    else -> TopTrackGridFragment()
                }
            }
            else -> TopTrackGridFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "4 Weeks"
            1 -> "6 Months"
            2 -> "All Time"
            else -> null
        }
    }
}
