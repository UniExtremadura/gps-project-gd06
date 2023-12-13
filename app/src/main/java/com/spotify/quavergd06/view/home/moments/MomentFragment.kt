package com.spotify.quavergd06.view.home.moments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.spotify.quavergd06.R
import com.spotify.quavergd06.data.MomentsRepository

import com.spotify.quavergd06.databinding.FragmentMomentBinding
import com.spotify.quavergd06.data.model.Moment
import com.spotify.quavergd06.database.QuaverDatabase
import kotlinx.coroutines.launch

class MomentFragment : Fragment() {

    private lateinit var listener: OnMomentClickListener
    private lateinit var db : QuaverDatabase
    private lateinit var repository : MomentsRepository
    interface OnMomentClickListener {
        fun onMomentClick(moment: Moment)
    }

    interface onMapButtonListener {
        fun onMapButtonClick()
    }

    private var _binding: FragmentMomentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MomentAdapter

    private var moments = emptyList<Moment>()

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        db = QuaverDatabase.getInstance(requireContext())
        repository = MomentsRepository.getInstance(db.momentDAO())
        if (context is OnMomentClickListener)
            listener = context
        else {
            throw RuntimeException("$context must implement OnMomentClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMomentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun subscribeUi(adapter : MomentAdapter) {
        repository.moments.observe(viewLifecycleOwner) { moments ->
            adapter.updateData(moments)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        val button = binding.buttonToMap as FloatingActionButton
        button.setOnClickListener {
            navigateToMapFragment()
        }
        setUpRecyclerView()
        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterMoments(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterMoments(newText)
                return false
            }
        })
        subscribeUi(adapter)
    }
    private fun setUpRecyclerView() {
        adapter = MomentAdapter(moments = moments, onClick = {
            listener.onMomentClick(it)
        })
        with(binding) {
            momentShowGrid.layoutManager = GridLayoutManager(context, 2)
            momentShowGrid.adapter = adapter
        }
        android.util.Log.d("DiscoverFragment", "setUpRecyclerView")
    }

    private fun navigateToMapFragment() {
        // Encuentra el NavController y navega a la acción definida en el gráfico de navegación
        findNavController().navigate(R.id.action_momentFragment_to_mapFragment)
    }

    private fun filterMoments(query: String?) {
        val filteredMoments = moments.filter {
            it.title.contains(query.orEmpty(), ignoreCase = true)
        }
        adapter.updateData(filteredMoments)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }

}