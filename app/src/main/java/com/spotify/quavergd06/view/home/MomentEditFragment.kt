package com.spotify.quavergd06.view.home

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.spotify.quavergd06.model.Moment
import java.text.SimpleDateFormat
import java.util.Locale
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.Manifest
import android.content.ClipData.Item
import android.content.Context
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import com.spotify.quavergd06.R
import com.spotify.quavergd06.databinding.FragmentMomentEditBinding
import androidx.appcompat.widget.SearchView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.spotify.quavergd06.api.getNetworkService
import com.spotify.quavergd06.api.setKey
import com.spotify.quavergd06.data.api.ArtistItem
import com.spotify.quavergd06.data.api.Items
import com.spotify.quavergd06.data.api.Tracks
import com.spotify.quavergd06.data.toArtist
import kotlinx.coroutines.launch

class MomentEditFragment : Fragment() {

    private var _binding: FragmentMomentEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMomentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val autoCompleteTextView = binding.detailSongTitle


        lifecycleScope.launch {
            try {
                setKey(obtenerSpotifyApiKey(requireContext())!!)
                var opciones = fetchTracks()
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, opciones)
                autoCompleteTextView.setAdapter(adapter)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
            }
        }


        val moment = arguments?.getSerializable("moment") as? Moment
        //TODO: Adecuar la carga según el origen de los datos
        if (moment != null) {
            moment?.let {
                // Configurar la vista con los detalles del Momento
                binding.detailImage.setImageResource(it.image)
                autoCompleteTextView.setText(it.songTitle)
                binding.detailLocation.text = it.location
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(it.date)
                binding.detailDate.text = formattedDate

                binding.detailTitle.setText(it.title)
                // Configurar otros elementos según sea necesario
            }
        } else {
            openCamera()
        }
    }

    override fun onResume() {
        super.onResume()

        // Ocultar BottomNavigationView
        val navBar: BottomNavigationView? = activity?.findViewById(R.id.bottom_navigation)
        navBar?.visibility = View.GONE
    }

    private fun obtenerSpotifyApiKey(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("access_token", null)
    }

    private fun openCamera() {
        // Verificar si se tienen los permisos necesarios
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Solicitar permisos si no están otorgados
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
            //reload fragment

        } else {
            // Abrir la cámara
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            binding.detailImage.setImageBitmap(imageBitmap)
        }
    }

    private suspend fun fetchTracks(): List<String> {
        var apiTracks = listOf <Items>()
        var apiTrackNames = listOf<String>()
        try {
            apiTracks = getNetworkService().loadTracks().body()?.items ?: emptyList()
        } catch (cause: Throwable) {
            //throw APIException("Unable to fetch data from API", cause)
        }
        for (item in apiTracks) {
            if (item.type.equals("track")) {
                apiTrackNames += item.name.toString()
            }
        }
        return apiTrackNames
    }

    companion object {
        private const val CAMERA_REQUEST_CODE = 123
        private const val CAMERA_PERMISSION_REQUEST_CODE = 456
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