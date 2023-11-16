package com.spotify.quavergd06.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
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

        return view
    }

    private fun configureWebView() {
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
    }
}
