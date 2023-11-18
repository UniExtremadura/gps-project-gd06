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
import android.content.Context
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import com.spotify.quavergd06.R
import com.spotify.quavergd06.databinding.FragmentMomentEditBinding
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.spotify.quavergd06.api.getNetworkService
import com.spotify.quavergd06.api.setKey
import com.spotify.quavergd06.data.api.Items
import kotlinx.coroutines.launch
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.fragment.findNavController
import com.spotify.quavergd06.database.QuaverDatabase

class MomentEditFragment : Fragment() {

    private var _binding: FragmentMomentEditBinding? = null
    private val binding get() = _binding!!
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    private lateinit var db: QuaverDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMomentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = QuaverDatabase.getInstance(requireContext())!!
        val autoCompleteTextView = binding.detailSongTitle

        binding.buttonSave.setOnClickListener {
            persistMoment()
            findNavController().navigateUp()
        }

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
            binding.detailDate.text = dateFormat.format(java.util.Date())
            obtenerUbicacion().let { (latitud, longitud) ->
                binding.detailLocation.text = "$latitud, $longitud"
            }
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

    private fun obtenerUbicacion(): Pair<Double, Double> {
        // Obtén una instancia del LocationManager
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Variables para almacenar latitud y longitud
        var latitud: Double = 0.0
        var longitud: Double = 0.0

        // Verifica si se tienen permisos de ubicación
        if (requireActivity().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED) {

            // Obtiene la última ubicación conocida (puede ser nula)
            val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            // Verifica si se obtuvo la última ubicación conocida
            if (lastKnownLocation != null) {
                latitud = lastKnownLocation.latitude
                longitud = lastKnownLocation.longitude
            } else {
                // Si la última ubicación conocida es nula, solicita actualizaciones de ubicación
                val locationListener = object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        latitud = location.latitude
                        longitud = location.longitude

                        // Detener las actualizaciones de ubicación después de obtener la primera ubicación
                        locationManager.removeUpdates(this)
                    }
                }
                // Solicita actualizaciones de ubicación
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
            }
        } else {
            // Si no se tienen permisos, solicítalos al usuario
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }

        // Retorna un objeto Pair con las coordenadas
        return Pair(latitud, longitud)
    }

    private fun persistMoment() {
        val latLong = extractLatLongFromLocation(binding.detailLocation.text.toString())
        val (_latitude, _longitude) = latLong!!
        with(binding) {
            lifecycleScope.launch {
                val moment = Moment(
                    momentId = null,
                    title = detailTitle.text.toString(),
                    date = dateFormat.parse(detailDate.text.toString()),
                    songTitle = detailSongTitle.text.toString(),
                    image = R.drawable.ic_launcher_foreground,
                    location = "",
                    latitude = _latitude,
                    longitude = _longitude,
                    imageBitmap = detailImage.drawable.toBitmap()
                )
                db.momentDAO().insertMoment(moment)
            }
        }
    }

    fun extractLatLongFromLocation(location: String): Pair<Double, Double>? {
        val latLongRegex = """(-?\d+(\.\d+)?)\s*,\s*(-?\d+(\.\d+)?)""".toRegex()
        val matchResult = latLongRegex.find(location)

        return matchResult?.let {
            val (latitude, _, longitude, _) = it.destructured
            Pair(latitude.toDouble(), longitude.toDouble())
        }
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