package com.spotify.quavergd06.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.spotify.quavergd06.R

class MapFragment : Fragment() {

    private lateinit var webView: WebView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        webView = view.findViewById(R.id.osmWebView)
        configureWebView()

        // Carga el mapa de OpenStreetMap
        val osmUrl = "https://www.openstreetmap.org"
        webView.loadUrl(osmUrl)

        val button = view?.findViewById(R.id.buttonToMoment) as Button
        button.setOnClickListener {
            navigateToMomentFragment()
        }

        return view


    }

    private fun configureWebView() {
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
    }

    private fun navigateToMomentFragment() {
        // Encuentra el NavController y navega a la acción definida en el gráfico de navegación
        findNavController().navigate(R.id.action_mapFragment_to_momentFragment)
    }
}
