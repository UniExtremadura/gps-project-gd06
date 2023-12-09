package com.spotify.quavergd06.view.home.moments

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.spotify.quavergd06.R
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import com.spotify.quavergd06.database.QuaverDatabase
import com.spotify.quavergd06.data.model.Moment
import kotlinx.coroutines.launch

class MapFragment : Fragment() {

    private lateinit var mapView: MapView
    private lateinit var db: QuaverDatabase
    private var moments = emptyList<Moment>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = QuaverDatabase.getInstance(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        mapView = view.findViewById(R.id.osmMapView)
        configureMapView()
        lifecycleScope.launch {
            moments = db.momentDAO().getAllMoments()
            if (moments.isNotEmpty()) {
                loadMapMoments()
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view?.findViewById(R.id.buttonToMoment) as FloatingActionButton
        button.setOnClickListener {
            navigateToMomentFragment()
        }
    }

    private fun configureMapView() {
        // Configura el mapa y la vista
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setBuiltInZoomControls(true)
        mapView.setMultiTouchControls(true)

        // Centra el mapa en una ubicación inicial
        //TODO: Cambiar por la ubicación actual del usuario
        mapView.controller.setCenter(GeoPoint(39.4689266, -6.3761098))
        mapView.controller.setZoom(15.0)
        Configuration.getInstance().load(context, context?.getSharedPreferences("osmdroid", 0))

    }

    private fun loadMapMoments() {
        // Carga de momentos en el mapa
        var avgLat = 0.0
        var avgLong = 0.0
        for (moment in moments) {
            avgLat += moment.latitude
            avgLong += moment.longitude
            val marker = Marker(mapView)
            marker.position = GeoPoint(moment.latitude, moment.longitude)
            marker.title = moment.title

            // Carga la imagen desde una uri en el bitmap
            val bitmap = getBitmapFromUri(requireContext().contentResolver, moment.imageURI)
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap!!, 120, 120, false)

            // Configura el ícono del marcador con la imagen redimensionada
            marker.icon = BitmapDrawable(resources, scaledBitmap)

            // Ajusta el punto de anclaje al centro inferior de la imagen del marcador
            marker.setAnchor(0.5f, 1.0f)

            // Agrega un OnMarkerClickListener al marcador
            marker.setOnMarkerClickListener { marker, mapView ->
                // Navega al MomentDetailFragment y pasa el momento como argumento
                showMomentDetailFragment(moment)
                true
            }

            // Agrega el marcador al mapa
            mapView.overlays.add(marker)
        }
        // Centra el mapa en la ubicación promedio de los momentos
        mapView.controller.setCenter(GeoPoint(avgLat / moments.size, avgLong / moments.size))

    }

    fun getBitmapFromUri(contentResolver: ContentResolver, uriString: String): Bitmap? {
        return try {
            // Utiliza la ContentResolver para abrir la entrada de datos en la URI
            val inputStream = contentResolver.openInputStream(uriString.toUri())

            // Decodifica la corriente de entrada en un Bitmap
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
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
    private fun navigateToMomentFragment() {
        // Encuentra el NavController y navega a la acción definida en el gráfico de navegación
        findNavController().navigate(R.id.action_mapFragment_to_momentFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDetach()
    }
}
