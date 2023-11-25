import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.spotify.quavergd06.data.fetchables.Fetchable
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.view.home.TopGridFragment

class TopItemGridPagerAdapter(fragmentManager: FragmentManager, private val fetchable: Fetchable, private val onClick: (StatsItem) -> Unit) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TopGridFragment.newInstance("short_term", fetchable, onClick) // 4 weeks
            1 -> TopGridFragment.newInstance("medium_term", fetchable, onClick) // 6 months
            2 -> TopGridFragment.newInstance("long_term", fetchable, onClick) // All time
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
