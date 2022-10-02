package com.ariefzuhri.gamedisc.domain.usecase

import androidx.paging.PagingData
import com.ariefzuhri.gamedisc.domain.enums.Platform
import com.ariefzuhri.gamedisc.domain.model.Game
import com.ariefzuhri.gamedisc.domain.repository.IGameRepository
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class SearchGamesInteractor @Inject constructor(
    private val gameRepository: IGameRepository,
) : SearchGamesUseCase {

    override fun searchGames(query: String): Flowable<PagingData<Game>> {
        return gameRepository.searchGames(
            pageSize = 10,
            platformIds = listOf(Platform.PC),
            query = query
        )
    }
}