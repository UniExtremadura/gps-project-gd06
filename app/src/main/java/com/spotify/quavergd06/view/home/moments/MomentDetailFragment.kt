package com.spotify.quavergd06.view.home.moments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.spotify.quavergd06.databinding.FragmentMomentDetailBinding
import com.spotify.quavergd06.data.model.Moment
import java.text.SimpleDateFormat
import java.util.Locale
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.spotify.quavergd06.R
import com.spotify.quavergd06.api.APIError
import com.spotify.quavergd06.data.MomentsRepository
import com.spotify.quavergd06.database.QuaverDatabase
import kotlinx.coroutines.launch


class MomentDetailFragment : Fragment() {

    private val viewModel: MomentDetailViewModel by viewModels { MomentDetailViewModel.Factory }
    private lateinit var onMomentEditClickListener: OnMomentEditClickListener
    private var _binding: FragmentMomentDetailBinding? = null
    private val binding get() = _binding!!
    
    private val args: MomentDetailFragmentArgs by navArgs()

    interface OnMomentEditClickListener {
        fun onMomentEditClick(moment : Moment)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMomentEditClickListener)
            onMomentEditClickListener = context
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
        val moment = args.moment

        viewModel.moment = moment

        subscribeUi()

        binding.buttonDelete.setOnClickListener {
            moment.momentId?.let { momentId ->
                showDeleteConfirmationDialog(momentId)
            }
        }

        viewModel.deletionComplete.observe(viewLifecycleOwner) { isDeletionComplete ->
            if (isDeletionComplete) {
                findNavController().navigateUp()
            }
        }

    }

    private fun subscribeUi() {
        viewModel.momentDetail.observe(viewLifecycleOwner) { moment ->
            moment?.let { momentBinding(it) }
        }
    }

    private fun momentBinding(moment: Moment) {

        // Configurar la vista con los detalles del Momento
        Glide.with(this)
            .load(moment.imageURI)
            .into(binding.detailImage)

        binding.detailSongTitle.text = moment.songTitle
        binding.detailLocation.text = moment.location

        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val formattedDate = moment.date?.let { dateFormat.format(it) }
        binding.detailDate.text = formattedDate

        binding.detailTitle.text = moment.title

        binding.buttonEdit.setOnClickListener {
            onMomentEditClickListener.onMomentEditClick(moment)
        }

        binding.buttonDelete.setOnClickListener {
            moment.momentId?.let { it1 -> showDeleteConfirmationDialog(it1) }
        }

    }

    private fun showDeleteConfirmationDialog(momentId : Long) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Confirmar eliminación")
        alertDialogBuilder.setMessage("¿Estás seguro de que deseas eliminar este momento?")
        alertDialogBuilder.setPositiveButton("Sí") { dialog, which ->
            // Usuario hizo clic en Sí, eliminar el momento
            viewModel.deleteMoment(momentId)
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