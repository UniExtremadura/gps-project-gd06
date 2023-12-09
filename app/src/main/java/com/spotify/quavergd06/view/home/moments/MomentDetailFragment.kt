package com.spotify.quavergd06.view.home.moments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.spotify.quavergd06.databinding.FragmentMomentDetailBinding
import com.spotify.quavergd06.data.model.Moment
import java.text.SimpleDateFormat
import java.util.Locale
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.spotify.quavergd06.R
import com.spotify.quavergd06.database.QuaverDatabase
import kotlinx.coroutines.launch


class MomentDetailFragment : Fragment() {

    private var _binding: FragmentMomentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var db : QuaverDatabase

    private var momentId: Long = 0
    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = QuaverDatabase.getInstance(requireContext())
    }

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
            momentId = it.momentId!!
            // Configurar la vista con los detalles del Momento
            Glide.with(this)
                .load(moment.imageURI)
                .into(binding.detailImage)
            binding.detailSongTitle.text = it.songTitle
            binding.detailLocation.text = it.location

            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val formattedDate = dateFormat.format(it.date)
            binding.detailDate.text = formattedDate// ...

            binding.detailTitle.text = it.title
            binding.buttonEdit.setOnClickListener {
                navigateToEditFragment()
            }

            binding.buttonDelete.setOnClickListener {
                showDeleteConfirmationDialog()
            }
        }
    }

    private fun deleteMoment() {
        lifecycleScope.launch {
            db.momentDAO().deleteMoment(momentId)
            findNavController().navigateUp()
        }

    }

    private fun navigateToEditFragment() {
        val bundle = Bundle()
        val moment = arguments?.getSerializable("moment") as? Moment
        bundle.putSerializable("moment", moment)
        findNavController().navigate(R.id.action_momentDetailFragment_to_momentEditFragment, bundle)
    }

    private fun showDeleteConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Confirmar eliminación")
        alertDialogBuilder.setMessage("¿Estás seguro de que deseas eliminar este momento?")
        alertDialogBuilder.setPositiveButton("Sí") { dialog, which ->
            // Usuario hizo clic en Sí, eliminar el momento
            deleteMoment()
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, which ->
            // Usuario hizo clic en No, cerrar el cuadro de diálogo sin hacer nada
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
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