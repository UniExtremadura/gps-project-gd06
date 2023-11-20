package com.spotify.quavergd06.view.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.spotify.quavergd06.api.setKey
import com.spotify.quavergd06.data.fetchables.Fetchable
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.databinding.FragmentTopGridBinding
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class HistoryListFragment() : Fragment() {
    private var _binding: FragmentTopGridBinding? = null
    private val binding get() = _binding!!

    //private lateinit var adapter: Adapter
    private var items: List<StatsItem> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopGridBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
    }
    // Fetch data based on the specified time period
//        lifecycleScope.launch {
//            try {
//                setKey(obtenerSpotifyApiKey(requireContext())!!)
//                items = fetchable.fetch(timePeriod)
//                adapter.updateData(items)
//            } catch (e: Exception) {
//                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
//            }
//        }


    private fun setUpRecyclerView() {
//        adapter = StatsItemAdapter(
//            statsItems = items,
//            context = this.context,
//            onClick = {statsItem -> onClick(statsItem) }
//        )
        with(binding) {
            topShowGrid.layoutManager = GridLayoutManager(context, 2)
        }
        //            topShowGrid.adapter = adapter

//        android.util.Log.d("ArtistFragment", "setUpRecyclerView")
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


}