package com.spotify.quavergd06.data

import com.spotify.quavergd06.model.Moment
import java.util.Date
import com.spotify.quavergd06.R
class DummyData {

    val dummyMoments: List<Moment> = listOf(
        Moment(
            "Mi primer Momento",
            Date(2003, 12, 15),
            "Este es mi primer momento",
            R.drawable.image_mobile_phone
        ),
        Moment(
            "Mi segundo Momento",
            Date(2002, 7, 20),
            "Este es mi segundo momento",
            R.drawable.landscape
        ),
        Moment(
            "Mi tercer Momento",
            Date(2002, 8, 13),
            "Este es mi tercer momento",
            R.drawable.microsoft
        )
    )
}