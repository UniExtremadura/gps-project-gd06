package com.spotify.quavergd06.view.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.spotify.quavergd06.R
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import com.spotify.quavergd06.data.dummy.dummyMoments
import com.spotify.quavergd06.model.Moment

class MapFragment : Fragment() {

    private lateinit var mapView: MapView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        mapView = view.findViewById(R.id.osmMapView)
        configureMapView()


        val button = view?.findViewById(R.id.buttonToMoment) as Button
        button.setOnClickListener {
            navigateToMomentFragment()
        }

        return view


    }

    private fun configureMapView() {
        // Configura el mapa y la vista
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setBuiltInZoomControls(true)
        mapView.setMultiTouchControls(true)

        // Centra el mapa en una ubicación inicial
        //TODO: Cambiar por la ubicación actual del usuario
        mapView.controller.setCenter(GeoPoint(39.4689266,-6.3761098))
        mapView.controller.setZoom(15.0)
        Configuration.getInstance().load(context, context?.getSharedPreferences("osmdroid", 0))

        // Carga de momentos en el mapa
        for (moment in dummyMoments) {
            val marker = Marker(mapView)
            marker.position = GeoPoint(moment.latitude, moment.longitude)
            marker.title = moment.title

            // Carga la imagen del recurso y redimensiona
            val originalBitmap = BitmapFactory.decodeResource(resources, moment.image)
            val scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, 120, 120, false)

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
