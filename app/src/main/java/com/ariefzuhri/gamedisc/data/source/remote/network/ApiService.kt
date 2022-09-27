package com.ariefzuhri.gamedisc.data.source.remote.network

import com.ariefzuhri.gamedisc.BuildConfig
import com.ariefzuhri.gamedisc.data.source.remote.response.GamesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(value = "games?key=${BuildConfig.rawgApiKey}&ordering=-rating")
    fun getTopRatedGames(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("platforms") platformIds: String,
    ): Single<GamesResponse>

    @GET(value = "games?key=${BuildConfig.rawgApiKey}&ordering=-released")
    fun getLatestReleasedGames(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("platforms") platformIds: String,
        @Query("dates") dateRanges: String,
    ): Single<GamesResponse>

    @GET(value = "games?key=${BuildConfig.rawgApiKey}")
    fun searchGames(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("platforms") platformIds: String,
        @Query("search") query: String,
    ): Single<GamesResponse>
}