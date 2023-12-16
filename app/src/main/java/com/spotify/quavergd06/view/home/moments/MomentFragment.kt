package com.spotify.quavergd06.view.home.moments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.spotify.quavergd06.databinding.FragmentMomentBinding
import com.spotify.quavergd06.data.model.Moment
import com.spotify.quavergd06.view.home.HomeViewModel


class MomentFragment : Fragment() {

    private lateinit var mapListener: OnMapButtonListener
    private val viewModel: MomentViewModel by viewModels { MomentViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()

    interface OnMapButtonListener {
        fun onMapButtonClick()
    }

    private var _binding: FragmentMomentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MomentAdapter

    private var moments = emptyList<Moment>()

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        if (context is OnMapButtonListener)
            mapListener = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMomentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun subscribeUi(adapter : MomentAdapter) {
        viewModel.moments.observe(viewLifecycleOwner) { moments ->
            adapter.updateData(moments)
        }

        viewModel.filteredMoments.observe(viewLifecycleOwner) { filteredMoments ->
            adapter.updateData(filteredMoments)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        val button = binding.buttonToMap as FloatingActionButton
        button.setOnClickListener {
            mapListener.onMapButtonClick()
        }

        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.setFilterQuery(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                viewModel.setFilterQuery(query)
                return false
            }
        })

        subscribeUi(adapter)
    }
    private fun setUpRecyclerView() {
        adapter = MomentAdapter(
            moments = moments,
            onClick = {
                homeViewModel.onMomentClick(it)
        }
        )
        with(binding) {
            momentShowGrid.layoutManager = GridLayoutManager(context, 2)
            momentShowGrid.adapter = adapter
        }
        android.util.Log.d("DiscoverFragment", "setUpRecyclerView")
    }

/*    private fun filterMoments(query: String?) {
        val filteredMoments = moments.filter {
            it.title.contains(query.orEmpty(), ignoreCase = true)
        }
        adapter.updateData(filteredMoments)
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }

}