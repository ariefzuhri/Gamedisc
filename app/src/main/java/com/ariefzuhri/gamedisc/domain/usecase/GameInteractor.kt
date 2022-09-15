package com.ariefzuhri.gamedisc.domain.usecase

import com.ariefzuhri.gamedisc.domain.model.Game
import com.ariefzuhri.gamedisc.common.helper.Resource
import com.ariefzuhri.gamedisc.domain.repository.IGameRepository
import io.reactivex.rxjava3.core.Flowable

class GameInteractor(private val gameRepository: IGameRepository) : GameUseCase {

    override fun getTopRatedGames(): Flowable<Resource<List<Game>>> {
        return gameRepository.getTopRatedGames()
    }

    override fun getLatestReleasedGames(): Flowable<Resource<List<Game>>> {
        return gameRepository.getLatestReleasedGames()
    }

    override fun searchGames(query: String): Flowable<Resource<List<Game>>> {
        return gameRepository.searchGames(query)
    }
}