package com.ariefzuhri.gamedisc.domain.usecase

import androidx.paging.PagingData
import com.ariefzuhri.gamedisc.domain.model.Game
import io.reactivex.rxjava3.core.Flowable

interface SeeLatestReleasedGamesUseCase {

    fun getLatestReleasedGames(): Flowable<PagingData<Game>>
}