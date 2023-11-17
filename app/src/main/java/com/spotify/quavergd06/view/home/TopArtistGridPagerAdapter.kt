import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.spotify.quavergd06.view.home.TopArtistsFragment

class TopArtistGridPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = 3 // Three tabs for 4 weeks, 6 months, and all time

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TopArtistsFragment.newInstance("short_term") // 4 weeks
            1 -> TopArtistsFragment.newInstance("medium_term") // 6 months
            2 -> TopArtistsFragment.newInstance("long_term") // All time
            else -> throw IllegalArgumentException("Invalid position: $position")
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
