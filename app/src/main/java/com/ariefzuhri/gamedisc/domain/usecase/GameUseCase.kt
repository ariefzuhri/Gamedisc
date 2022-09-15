package com.ariefzuhri.gamedisc.domain.usecase

import com.ariefzuhri.gamedisc.domain.model.Game
import com.ariefzuhri.gamedisc.common.helper.Resource
import io.reactivex.rxjava3.core.Flowable

interface GameUseCase {

    fun getTopRatedGames(): Flowable<Resource<List<Game>>>

    fun getLatestReleasedGames(): Flowable<Resource<List<Game>>>

    fun searchGames(query: String): Flowable<Resource<List<Game>>>
}