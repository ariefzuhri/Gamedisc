package com.ariefzuhri.gamedisc.domain.repository

import com.ariefzuhri.gamedisc.domain.model.Game
import com.ariefzuhri.gamedisc.common.helper.Resource
import io.reactivex.rxjava3.core.Flowable

interface IGameRepository {

    fun getTopRatedGames(): Flowable<Resource<List<Game>>>

    fun getLatestReleasedGames(): Flowable<Resource<List<Game>>>

    fun searchGames(query: String): Flowable<Resource<List<Game>>>
}