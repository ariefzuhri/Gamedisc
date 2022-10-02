package com.ariefzuhri.gamedisc.domain.usecase

import androidx.paging.PagingData
import com.ariefzuhri.gamedisc.common.util.getCurrentDate
import com.ariefzuhri.gamedisc.common.util.subtractDate
import com.ariefzuhri.gamedisc.domain.enums.DateFormat
import com.ariefzuhri.gamedisc.domain.enums.Platform
import com.ariefzuhri.gamedisc.domain.model.Game
import com.ariefzuhri.gamedisc.domain.repository.IGameRepository
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class SeeLatestReleasedGamesInteractor @Inject constructor(
    private val gameRepository: IGameRepository,
) : SeeLatestReleasedGamesUseCase {

    override fun getLatestReleasedGames(): Flowable<PagingData<Game>> {
        val startDate = getCurrentDate(DateFormat.DATE_RAW_LONG)
        val endDate = addDate(startDate, DateFormat.DATE_RAW_LONG, 6)
        return gameRepository.getLatestReleasedGames(
            pageSize = 10,
            platformIds = listOf(Platform.PC),
            startDate = startDate,
            endDate = endDate
        )
    }
}