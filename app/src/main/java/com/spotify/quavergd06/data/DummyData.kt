package com.spotify.quavergd06.data

import com.spotify.quavergd06.model.Moment
import java.util.Date
import com.spotify.quavergd06.R

// TODO CAMBIADO COSAS DE COMMIT ANTERIOR, CUIDADO AL PASARLO AL BUENO
val dummyMoments: List<Moment> = listOf(
    Moment(
        "Mi primer Momento",
        Date(2003, 12, 15),
        "Este es mi primer momento",
        R.drawable.image_mobile_phone,
        39.4747451,-6.3712429  // Coordenadas de ejemplo para el primer momento
    ),
    Moment(
        "Mi segundo Momento",
        Date(2002, 7, 20),
        "Este es mi segundo momento",
        R.drawable.landscape,
        39.4734895,-6.3833371  // Coordenadas de ejemplo para el segundo momento
    ),
    Moment(
        "Mi tercer Momento",
        Date(2002, 8, 13),
        "Este es mi tercer momento",
        R.drawable.microsoft,
        39.4788312,-6.3435945  // Coordenadas de ejemplo para el tercer momento
    )
)
