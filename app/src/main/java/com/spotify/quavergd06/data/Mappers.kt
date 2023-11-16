package com.spotify.quavergd06.data

import com.spotify.quavergd06.data.api.ArtistItem
import com.spotify.quavergd06.data.model.Artist

fun ArtistItem.toArtist() = Artist(
    id = id ?: "null",
    name = name,
    popularity = popularity,
    genres = genres,
    imageUrls = images.map { it.url ?: "null" } as ArrayList<String>
)
