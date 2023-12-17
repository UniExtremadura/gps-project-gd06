package com.spotify.quavergd06.data

import com.spotify.quavergd06.data.api.ArtistItem
import com.spotify.quavergd06.data.api.TrackItem
import com.spotify.quavergd06.data.api.UserProfileInfoResponse
import com.spotify.quavergd06.data.model.Artist
import com.spotify.quavergd06.data.model.StatsItem
import com.spotify.quavergd06.data.model.Track
import com.spotify.quavergd06.data.model.User
import com.spotify.quavergd06.database.StringListWrapper

fun ArtistItem.toArtist() = Artist(
    artistId = id ?: "null",
    name = name,
    popularity = popularity,
    genres = StringListWrapper(genres),
    imageUrls = StringListWrapper(images.map { it.url ?: "null" } as ArrayList<String>)
)

fun TrackItem.toTrack() = Track(
    trackId = id ?: "null",
    name = name,
    artistName = artists.firstOrNull()?.toArtist()?.name,
    artistId = artists.firstOrNull()?.id ?: "null",
    album = album?.name ?: "null",
    imageUrls = StringListWrapper(album?.images?.map { it.url ?: "null" } as ArrayList<String>),
    position = 0,
    timeRange = "null",
    type = "null"
)

fun Artist.toStatsItem() = StatsItem(
    id = artistId,
    name = name,
    imageUrls = imageUrls.list,
    genres = genres.list,
    popularity = popularity
)

fun Track.toStatsItem() = StatsItem(
    id = trackId,
    name = name,
    imageUrls = imageUrls.list,
    album = album,
    artistId = artistId,
    artistName = artistName
)


