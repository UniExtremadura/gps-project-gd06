package com.spotify.quavergd06.view.home.moments

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
import com.spotify.quavergd06.data.model.Moment
import java.text.SimpleDateFormat
import java.util.Locale
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import com.spotify.quavergd06.R
import com.spotify.quavergd06.databinding.FragmentMomentEditBinding
import android.widget.ArrayAdapter
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import android.text.InputFilter
import android.text.Spanned
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.spotify.quavergd06.view.home.HomeViewModel

class MomentEditFragment : Fragment() {

    private val viewModel: MomentEditViewModel by viewModels { MomentEditViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentMomentEditBinding? = null
    private val binding get() = _binding!!
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    private var momentId: Long? = null
    private var imageURI: String? = null

    private var suggestionsTrackNames = listOf<String?>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMomentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val maxTitleLength = 25 // Establece la longitud máxima permitida
        val maxSongLength = 40 // Establece la longitud máxima permitida
        // Crea un InputFilter para limitar la longitud del texto y eliminar saltos de línea
        val titleFilter = arrayOf(InputFilter.LengthFilter(maxTitleLength), NoNewlineInputFilter())
        val songFilter = arrayOf(InputFilter.LengthFilter(maxSongLength), NoNewlineInputFilter())

        // Aplica el InputFilter al EditText
        binding.detailTitle.filters = titleFilter
        binding.detailSongTitle.filters = songFilter

        viewModel.bottomNavigationViewVisibility.observe(viewLifecycleOwner) { visible ->
            updateBottomNavigationViewVisibility(visible)
        }

        val autoCompleteTextView = binding.detailSongTitle
        binding.cameraOverlay.setOnClickListener {
            getAllPermissions()
            openCamera()
        }

        binding.buttonSave.setOnClickListener {
            try{
                if (imageURI == null){
                    imageURI = saveImage(binding.detailImage.drawable.toBitmap())
                }
                persistMoment(imageURI!!)
            }
            catch (e: Exception){
                Log.d("ERROR", "El usuario no ha tomado una foto")
            }
            finally {
                homeViewModel.navigateFromEditToMoment(!homeViewModel.navigateFromEditToMoment.value!!)
            }
        }

        binding.buttonCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.tracks.observe(viewLifecycleOwner) { suggestions ->
            val trackNames = suggestions.map { it.name.toString() }
            suggestionsTrackNames += trackNames
            Log.d("ASDA", suggestionsTrackNames.toString())
            autoCompleteTextView.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, suggestionsTrackNames))
        }

        val moment = arguments?.getSerializable("moment") as? Moment
        viewModel.moment = moment
        if (viewModel.moment != null) {
            viewModel.moment?.let {
                imageURI = it.imageURI
                momentId = it.momentId
                // Configurar la vista con los detalles del Momento
                loadImageFromUri(it.imageURI)
                autoCompleteTextView.setText(it.songTitle)
                binding.detailLocation.text = it.location
                val formattedDate = dateFormat.format(it.date)
                binding.detailDate.text = formattedDate

                binding.detailTitle.setText(it.title)
                // Configurar otros elementos según sea necesario
            }
        } else {
            getAllPermissions()
            openCamera()
        }
    }

    private fun updateBottomNavigationViewVisibility(visible: Boolean) {
        val navBar: BottomNavigationView? = activity?.findViewById(R.id.bottom_navigation)
        navBar?.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        viewModel.setBottomNavigationViewVisibility(false)
    }

    override fun onPause() {
        super.onPause()
        viewModel.setBottomNavigationViewVisibility(true)
    }

    private fun obtenerSpotifyApiKey(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("access_token", null)
    }

    private fun loadImageFromUri(uri: String?) {
        if (uri != null) {
            Glide.with(this)
                .load(uri)
                .into(binding.detailImage)
        }
    }

    private fun openCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }
        }
    }

    private fun getAllPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_REQUEST_CODE
                )
            }
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("PERMISSION", "Camera permission granted")
            } else {
                homeViewModel.navigateFromEditToMoment(!homeViewModel.navigateFromEditToMoment.value!!)
            }
        }
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("PERMISSION", "Location permission granted")
            } else {
                homeViewModel.navigateFromEditToMoment(!homeViewModel.navigateFromEditToMoment.value!!)
            }
        }
    }
    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            binding.detailImage.setImageBitmap(imageBitmap)
            if (viewModel.moment == null) {
                binding.detailDate.text = dateFormat.format(java.util.Date())
                getAllPermissions()
                obtenerUbicacion().let { (latitud, longitud) ->
                    if (latitud == 0.0 && longitud == 0.0) {
                        obtenerUbicacion().let { (latitud, longitud) ->
                            binding.detailLocation.text = "$latitud, $longitud"
                        }
                    }
                    binding.detailLocation.text = "$latitud, $longitud"
                }
            }
        }
    }

    private fun obtenerUbicacion(): Pair<Double, Double> {
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Variables para almacenar latitud y longitud
        var latitud = 0.0
        var longitud = 0.0

        // Verifica si se tienen permisos de ubicación
        if (requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val locationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    latitud = location.latitude
                    longitud = location.longitude

                    // Detener las actualizaciones de ubicación después de obtener la primera ubicación
                    locationManager.removeUpdates(this)
                }
            }

            // Intenta obtener la ubicación del proveedor GPS
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
                val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (lastKnownLocation != null) {
                    latitud = lastKnownLocation.latitude
                    longitud = lastKnownLocation.longitude
                }
            } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                // Si el proveedor GPS no está disponible, intenta obtener la ubicación del proveedor de red
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)
                val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (lastKnownLocation != null) {
                    latitud = lastKnownLocation.latitude
                    longitud = lastKnownLocation.longitude
                }
            }


        }
        return Pair(latitud, longitud)
    }

    private fun saveImage(imageBitmap: Bitmap?): String? {
        if (imageBitmap != null) {
            return MediaStore.Images.Media.insertImage(
                requireContext().contentResolver,
                imageBitmap,
                "Momento",
                "Momento de Quaver"
            )
        }
        return null
    }

    private fun persistMoment(_imageURI: String){
        val latLong = extractLatLongFromLocation(binding.detailLocation.text.toString())
        val (_latitude, _longitude) = latLong!!
        val moment = Moment(
            momentId = momentId,
            title = binding.detailTitle.text.toString(),
            date = dateFormat.parse(binding.detailDate.text.toString()),
            songTitle = binding.detailSongTitle.text.toString(),
            imageURI = _imageURI,
            location = binding.detailLocation.text.toString(),
            latitude = _latitude,
            longitude = _longitude,
        )
        viewModel.insertMoment(moment)
    }

    private fun extractLatLongFromLocation(location: String): Pair<Double, Double>? {
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
        private const val LOCATION_PERMISSION_REQUEST_CODE = 789
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class NoNewlineInputFilter : InputFilter {
        override fun filter(
            source: CharSequence?,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            source?.let {
                for (i in start until end) {
                    if (it[i] == '\n') {
                        // Exclude newline characters
                        return ""
                    }
                }
            }
            return null // Keep the original input
        }
    }
}