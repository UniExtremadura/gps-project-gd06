package com.spotify.quavergd06.data.dummy

import com.spotify.quavergd06.data.model.Moment
import java.util.Date
import com.spotify.quavergd06.R

// TODO CAMBIADO COSAS DE COMMIT ANTERIOR, CUIDADO AL PASARLO AL BUENO
val dummyMoments: List<Moment> = listOf(
    Moment(1,
        "Mi primer Momento",
        Date(2023 - 1900, 11, 15),
        "Nombre de la canción 1", // Reemplaza con el nombre de la canción real
        R.drawable.image_mobile_phone.toString(),
        "39.4747451, -6.3712429", // Reemplaza con la ubicación real
        39.4747451, -6.3712429
    ),
    Moment(2,
        "Mi segundo Momento",
        Date(2023 - 1900, 6, 20),
        "Nombre de la canción 2",
        R.drawable.landscape.toString(),
        "39.4734895, -6.3833371",
        39.4734895, -6.3833371
    ),
    Moment(3,
        "Mi tercer Momento",
        Date(2023 - 1900, 7, 13),
        "Nombre de la canción 3",
        R.drawable.microsoft.toString(),
        "39.4788312, -6.3435945",
        39.4788312, -6.3435945
    )
)
