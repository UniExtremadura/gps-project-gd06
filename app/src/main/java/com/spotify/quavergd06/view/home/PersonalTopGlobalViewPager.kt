package com.spotify.quavergd06.view.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.NavArgs
import com.spotify.quavergd06.data.fetchables.Fetchable
import com.spotify.quavergd06.data.model.StatsItem

class PersonalTopGlobalViewPager(
    fragmentManager: FragmentManager,
    private val navigate: (Int, Fragment) -> Unit,
    private val navigateNoArgs: (Int) -> Unit

) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PersonalStatsFragment(navigate, navigateNoArgs)
            1 -> GlobalTopFragment(navigate)
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Personal"
            1 -> "Global Top"
            else -> null
        }
    }
}