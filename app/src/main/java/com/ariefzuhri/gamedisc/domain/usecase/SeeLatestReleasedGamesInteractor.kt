package com.ariefzuhri.gamedisc.domain.usecase

import androidx.paging.PagingData
import com.ariefzuhri.gamedisc.common.util.addDate
import com.ariefzuhri.gamedisc.common.util.getCurrentDate
import com.ariefzuhri.gamedisc.domain.enums.DateFormat
import com.ariefzuhri.gamedisc.domain.enums.Platform
import com.ariefzuhri.gamedisc.domain.model.Game
import com.ariefzuhri.gamedisc.domain.repository.IGameRepository
import io.reactivex.rxjava3.core.Flowable

class SeeLatestReleasedGamesInteractor(
    private val gameRepository: IGameRepository,
) : SeeLatestReleasedGamesUseCase {

    override fun getLatestReleasedGames(): Flowable<PagingData<Game>> {
        val startDate = getCurrentDate(DateFormat.DATE_RAW)
        val endDate = addDate(startDate, DateFormat.DATE_RAW, 6)
        return gameRepository.getLatestReleasedGames(
            pageSize = 10,
            platformIds = listOf(Platform.PC),
            startDate = startDate,
            endDate = endDate
        )
    }
}