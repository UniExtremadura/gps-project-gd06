package com.spotify.quavergd06.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager

import com.spotify.quavergd06.databinding.FragmentMomentBinding
import com.spotify.quavergd06.model.Moment
import com.spotify.quavergd06.data.dummyMoments

/**
 * A simple [Fragment] subclass.
 * Use the [MomentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MomentFragment : Fragment() {

    private lateinit var listener: OnMomentClickListener
    interface OnMomentClickListener {
        fun onMomentClick(moment: Moment)
    }

    private var _binding: FragmentMomentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MomentAdapter

    private val moments = dummyMoments

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMomentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }
    private fun setUpRecyclerView() {
        adapter = MomentAdapter(moments, onClick = {
                listener.onMomentClick(it)
            }
        )
        with(binding) {
            momentShowGrid.layoutManager = GridLayoutManager(context, 2)
            momentShowGrid.adapter = adapter
        }
        android.util.Log.d("DiscoverFragment", "setUpRecyclerView")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }

}