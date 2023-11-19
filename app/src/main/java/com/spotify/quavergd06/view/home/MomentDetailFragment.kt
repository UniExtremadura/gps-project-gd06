package com.spotify.quavergd06.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.spotify.quavergd06.databinding.FragmentMomentDetailBinding
import com.spotify.quavergd06.model.Moment
import java.text.SimpleDateFormat
import java.util.Locale
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.spotify.quavergd06.R


class MomentDetailFragment : Fragment() {

    private var _binding: FragmentMomentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMomentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val moment = arguments?.getSerializable("moment") as? Moment
        moment?.let {
            // Configurar la vista con los detalles del Momento
            binding.detailImage.setImageResource(it.image)
            binding.detailSongTitle.text = it.songTitle
            binding.detailLocation.text = it.location

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(it.date)
            binding.detailDate.text = formattedDate// ...

            binding.detailTitle.text = it.title
            // Configurar otros elementos según sea necesario
        }
    }
    override fun onResume() {
        super.onResume()

        // Ocultar BottomNavigationView
        val navBar: BottomNavigationView? = activity?.findViewById(R.id.bottom_navigation)
        navBar?.visibility = View.GONE
    }


    override fun onPause() {
        super.onPause()

        // Mostrar BottomNavigationView
        val navBar: BottomNavigationView? = activity?.findViewById(R.id.bottom_navigation)
        navBar?.visibility = View.VISIBLE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}