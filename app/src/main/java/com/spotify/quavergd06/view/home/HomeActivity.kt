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
import com.spotify.quavergd06.model.ThemeManager
import com.spotify.quavergd06.view.home.moments.MapFragment
import com.spotify.quavergd06.view.home.moments.MapFragmentDirections
import com.spotify.quavergd06.view.home.moments.MomentDetailFragment
import com.spotify.quavergd06.view.home.moments.MomentDetailFragmentDirections
import com.spotify.quavergd06.view.home.moments.MomentFragment
import com.spotify.quavergd06.view.home.moments.MomentFragmentDirections

class HomeActivity : AppCompatActivity(),
    MomentDetailFragment.OnMomentEditClickListener, MomentFragment.OnMapButtonListener, MapFragment.OnMomentButtonListener, MapFragment.OnMomentMapClickListener {
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

        viewModel.navigateToMomentDetail.observe(this) { moment ->
            moment?.let {
                onMomentClick(moment)
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

    private fun onMomentClick(moment : Moment) {
        val action = MomentFragmentDirections.actionMomentFragmentToMomentDetailFragment(moment)
        navController.navigate(action)
    }

    override fun onMomentMapClickListener(moment: Moment) {
        val action = MapFragmentDirections.actionMapFragmentToMomentDetailFragment(moment)
        navController.navigate(action)
    }

    override fun onMomentEditClick(moment : Moment) {
        val action = MomentDetailFragmentDirections.actionMomentDetailFragmentToMomentEditFragment(moment)
        navController.navigate(action)
    }

    override fun onMapButtonClick() {
        val action = MomentFragmentDirections.actionMomentFragmentToMapFragment()
        navController.navigate(action)
    }

    override fun onMomentButtonClick() {
        val action = MapFragmentDirections.actionMapFragmentToMomentFragment()
        navController.navigate(action)
    }

}