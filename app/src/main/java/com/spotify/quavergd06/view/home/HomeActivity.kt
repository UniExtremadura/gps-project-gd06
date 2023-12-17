package com.spotify.quavergd06.view.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.spotify.quavergd06.R
import com.spotify.quavergd06.databinding.ActivityHomeBinding
import com.spotify.quavergd06.notifications.NotificationScheduler
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.activity.viewModels
import com.spotify.quavergd06.data.model.Moment
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.data.model.User
import com.spotify.quavergd06.view.home.moments.MapFragmentDirections
import com.spotify.quavergd06.view.home.moments.MomentDetailFragmentDirections
import com.spotify.quavergd06.view.home.moments.MomentFragmentDirections
import com.spotify.quavergd06.view.home.stats.StatsFragmentDirections

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    private val viewModel: HomeViewModel by viewModels()
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }
    companion object {
        const val MY_CHANNEL_ID = "myChannel"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = this.getSharedPreferences("user_prefs", Context.MODE_PRIVATE).getString("access_token", null)
        viewModel.userInSession = User("33", token = token!!)

        viewModel.navigateFromMomentToDetail.observe(this) { moment ->
            moment?.let {
                fromMomentToDetail(moment)
            }
        }

        viewModel.navigateToMapFromMoment.observe(this) { fromMomentToMap() }

        viewModel.navigateToMomentEditFromDetail.observe(this) { moment ->
            moment?.let {
                fromDetailToEdit(moment)
            }
        }

        viewModel.navigateFromEditToMoment.observe(this) { fromEditToMoment() }

        viewModel.navigateFromMapToDetail.observe(this) { moment ->
            moment?.let {
                fromMapToDetail(moment)
            }
        }

        viewModel.navigateFromMapToMoment.observe(this) { fromMapToMoment() }

        viewModel.navigateFromStatsToTrackDetail.observe(this) { statsItem ->
            statsItem?.let {
                fromStatsToTrackDetail(statsItem)
            }
        }


        viewModel.navigateFromHistoryToTrackDetail.observe(this) { statsItem ->
            statsItem?.let {
                fromHistoryToTrackDetail(statsItem)
            }
        }

        setUpUI()

        NotificationScheduler.setReminder(this, NotificationScheduler::class.java)
        createChannel()
    }

    private fun createChannel() {
        Log.d("NotificationScheduler", "Setting reminder")


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MY_CHANNEL_ID,
                "MySuperChannel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "SUSCRIBETE"
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setUpUI() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        binding.bottomNavigation.setupWithNavController(navHostFragment.navController)
    }

    private fun fromMomentToDetail(moment: Moment) {
        val action = MomentFragmentDirections.actionMomentFragmentToMomentDetailFragment(moment)
        navController.navigate(action)
    }

    private fun fromMapToDetail(moment: Moment) {
        val action = MapFragmentDirections.actionMapFragmentToMomentDetailFragment(moment)
        navController.navigate(action)
    }

    private fun fromDetailToEdit(moment : Moment) {
        val action = MomentDetailFragmentDirections.actionMomentDetailFragmentToMomentEditFragment(moment)
        navController.navigate(action)
    }

    private fun fromMomentToMap() {
        val action = MomentFragmentDirections.actionMomentFragmentToMapFragment()
        navController.navigate(action)
    }

    private fun fromMapToMoment() {
        val action = MapFragmentDirections.actionMapFragmentToMomentFragment()
        navController.navigate(action)
    }

    private fun fromEditToMoment() {
        val action = MomentDetailFragmentDirections.actionMomentEditFragmentToMomentFragment()
        navController.navigate(action)
    }

    private fun fromStatsToTrackDetail(statsItem: StatsItem) {
        val action = StatsFragmentDirections.actionStatsFragmentToTrackInfoFragment(statsItem)
        navController.navigate(action)
    }

    private fun fromHistoryToTrackDetail(statsItem: StatsItem) {
        val action = StatsFragmentDirections.actionStatsFragmentToTrackInfoFragment(statsItem)
        navController.navigate(action)
    }

}