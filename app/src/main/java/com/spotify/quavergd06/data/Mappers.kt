package com.spotify.quavergd06.data

import com.spotify.quavergd06.data.api.ArtistItem
import com.spotify.quavergd06.data.api.ItemsHistory
import com.spotify.quavergd06.data.api.TrackItem
import com.spotify.quavergd06.data.model.Artist
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.data.model.Track

fun ArtistItem.toArtist() = Artist(
    id = id ?: "null",
    name = name,
    popularity = popularity,
    genres = genres,
    imageUrls = images.map { it.url ?: "null" } as ArrayList<String>
)

fun TrackItem.toTrack() = Track(
    id = id ?: "null",
    name = name,
    artist = artists.firstOrNull()?.toArtist(),
    album = album?.name ?: "null",
    imageUrls = album?.images?.map { it.url ?: "null" } as ArrayList<String>
)

fun Artist.toStatsItem() = StatsItem(
    id = id,
    name = name,
    imageUrls = imageUrls,
    genres = genres,
    popularity = popularity
)

fun Track.toStatsItem() = StatsItem(
    id = id,
    name = name,
    imageUrls = imageUrls,
    album = album,
    artist = artist
)

