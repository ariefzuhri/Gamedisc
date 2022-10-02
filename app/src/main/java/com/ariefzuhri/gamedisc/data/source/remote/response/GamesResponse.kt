package com.ariefzuhri.gamedisc.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GamesResponse(

    @Json(name = "next")
    val next: String? = null,

    @Json(name = "previous")
    val previous: String? = null,

    @Json(name = "count")
    val count: Int? = null,

    @Json(name = "user_platforms")
    val userPlatforms: Boolean? = null,

    @Json(name = "results")
    val results: List<ResultsItem?>? = null,

    @Json(name = "error")
    val error: String? = null,
) {

    @JsonClass(generateAdapter = true)
    data class ShortScreenshotsItem(

        @Json(name = "image")
        val image: String? = null,

        @Json(name = "id")
        val id: Int? = null,
    )

    @JsonClass(generateAdapter = true)
    data class AddedByStatus(

        @Json(name = "owned")
        val owned: Int? = null,

        @Json(name = "yet")
        val yet: Int? = null,

        @Json(name = "beaten")
        val beaten: Int? = null,

        @Json(name = "dropped")
        val dropped: Int? = null,

        @Json(name = "playing")
        val playing: Int? = null,

        @Json(name = "toplay")
        val toplay: Int? = null,
    )

    @JsonClass(generateAdapter = true)
    data class Platform(

        @Json(name = "name")
        val name: String? = null,

        @Json(name = "id")
        val id: Int? = null,

        @Json(name = "slug")
        val slug: String? = null,
    )

    @JsonClass(generateAdapter = true)
    data class GenresItem(

        @Json(name = "name")
        val name: String? = null,

        @Json(name = "id")
        val id: Int? = null,

        @Json(name = "slug")
        val slug: String? = null,
    )

    @JsonClass(generateAdapter = true)
    data class ParentPlatformsItem(

        @Json(name = "platform")
        val platform: Platform? = null,
    )

    @JsonClass(generateAdapter = true)
    data class Store(

        @Json(name = "name")
        val name: String? = null,

        @Json(name = "id")
        val id: Int? = null,

        @Json(name = "slug")
        val slug: String? = null,
    )

    @JsonClass(generateAdapter = true)
    data class ResultsItem(

        @Json(name = "added")
        val added: Int? = null,

        @Json(name = "rating")
        val rating: Double? = null,

        @Json(name = "metacritic")
        val metacritic: Int? = null,

        @Json(name = "playtime")
        val playtime: Int? = null,

        @Json(name = "short_screenshots")
        val shortScreenshots: List<ShortScreenshotsItem?>? = null,

        @Json(name = "platforms")
        val platforms: List<PlatformsItem?>? = null,

        @Json(name = "user_game")
        val userGame: Any? = null,

        @Json(name = "score")
        val score: String? = null,

        @Json(name = "rating_top")
        val ratingTop: Int? = null,

        @Json(name = "reviews_text_count")
        val reviewsTextCount: Int? = null,

        @Json(name = "ratings")
        val ratings: List<RatingsItem?>? = null,

        @Json(name = "genres")
        val genres: List<GenresItem?>? = null,

        @Json(name = "saturated_color")
        val saturatedColor: String? = null,

        @Json(name = "added_by_status")
        val addedByStatus: AddedByStatus? = null,

        @Json(name = "id")
        val id: Int? = null,

        @Json(name = "parent_platforms")
        val parentPlatforms: List<ParentPlatformsItem?>? = null,

        @Json(name = "ratings_count")
        val ratingsCount: Int? = null,

        @Json(name = "slug")
        val slug: String? = null,

        @Json(name = "released")
        val released: String? = null,

        @Json(name = "stores")
        val stores: List<StoresItem?>? = null,

        @Json(name = "suggestions_count")
        val suggestionsCount: Int? = null,

        @Json(name = "tags")
        val tags: List<TagsItem?>? = null,

        @Json(name = "background_image")
        val backgroundImage: String? = null,

        @Json(name = "tba")
        val tba: Boolean? = null,

        @Json(name = "dominant_color")
        val dominantColor: String? = null,

        @Json(name = "esrb_rating")
        val esrbRating: EsrbRating? = null,

        @Json(name = "name")
        val name: String? = null,

        @Json(name = "community_rating")
        val communityRating: Int? = null,

        @Json(name = "updated")
        val updated: String? = null,

        @Json(name = "clip")
        val clip: Any? = null,

        @Json(name = "reviews_count")
        val reviewsCount: Int? = null,
    )

    @JsonClass(generateAdapter = true)
    data class RatingsItem(

        @Json(name = "count")
        val count: Int? = null,

        @Json(name = "id")
        val id: Int? = null,

        @Json(name = "title")
        val title: String? = null,

        @Json(name = "percent")
        val percent: Double? = null,
    )

    @JsonClass(generateAdapter = true)
    data class PlatformsItem(

        @Json(name = "platform")
        val platform: Platform? = null,
    )

    @JsonClass(generateAdapter = true)
    data class StoresItem(

        @Json(name = "store")
        val store: Store? = null,
    )

    @JsonClass(generateAdapter = true)
    data class EsrbRating(

        @Json(name = "name_ru")
        val nameRu: String? = null,

        @Json(name = "name")
        val name: String? = null,

        @Json(name = "id")
        val id: Int? = null,

        @Json(name = "slug")
        val slug: String? = null,

        @Json(name = "name_en")
        val nameEn: String? = null,
    )

    @JsonClass(generateAdapter = true)
    data class TagsItem(

        @Json(name = "games_count")
        val gamesCount: Int? = null,

        @Json(name = "name")
        val name: String? = null,

        @Json(name = "language")
        val language: String? = null,

        @Json(name = "id")
        val id: Int? = null,

        @Json(name = "image_background")
        val imageBackground: String? = null,

        @Json(name = "slug")
        val slug: String? = null,
    )
}