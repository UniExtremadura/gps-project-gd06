package com.spotify.quavergd06.view.home

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

import com.spotify.quavergd06.databinding.FragmentMomentBinding
import com.spotify.quavergd06.model.Moment
import com.spotify.quavergd06.data.dummy.dummyMoments
import com.spotify.quavergd06.database.QuaverDatabase
import kotlinx.coroutines.launch

class MomentFragment : Fragment() {

    private lateinit var listener: OnMomentClickListener
    private lateinit var db : QuaverDatabase
    interface OnMomentClickListener {
        fun onMomentClick(moment: Moment)
    }

    private var _binding: FragmentMomentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MomentAdapter

    private var moments = emptyList<Moment>()

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        db = QuaverDatabase.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMomentBinding.inflate(inflater, container, false)
        adapter = MomentAdapter(moments, onClick = {
            listener.onMomentClick(it)
        }
        )
        val button = _binding!!.buttonToMap as FloatingActionButton
        button.setOnClickListener {
            navigateToMapFragment()
        }
        db = QuaverDatabase.getInstance(requireContext())!!
        lifecycleScope.launch {
            moments = db.momentDAO().getAllMoments()
            adapter.updateData(moments)
        }

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        listener = object : OnMomentClickListener {
            override fun onMomentClick(moment: Moment) {
                showMomentDetailFragment(moment)
            }
        }
    }
    private fun setUpRecyclerView() {
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

    private fun showMomentDetailFragment(moment: Moment) {
        // Navega al MomentDetailFragment y pasa el momento como argumento
        val momentDetailFragment = MomentDetailFragment()
        val bundle = Bundle()
        bundle.putSerializable("moment", moment)
        momentDetailFragment.arguments = bundle

        // Usa findNavController() para navegar al fragmento
        findNavController().navigate(R.id.momentDetailFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }

}