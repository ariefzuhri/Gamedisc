package com.ariefzuhri.gamedisc.domain.repository

import androidx.paging.PagingData
import com.ariefzuhri.gamedisc.domain.enums.Platform
import com.ariefzuhri.gamedisc.domain.model.Game
import io.reactivex.rxjava3.core.Flowable

interface IGameRepository {

    fun getTopRatedGames(
        pageSize: Int,
        platformIds: List<Platform>,
    ): Flowable<PagingData<Game>>

    fun getLatestReleasedGames(
        pageSize: Int,
        platformIds: List<Platform>,
        startDate: String,
        lastDate: String,
    ): Flowable<PagingData<Game>>

    fun searchGames(
        pageSize: Int,
        platformIds: List<Platform>,
        query: String,
    ): Flowable<PagingData<Game>>
}