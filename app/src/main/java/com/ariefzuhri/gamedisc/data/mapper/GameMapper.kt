package com.ariefzuhri.gamedisc.data.mapper

import com.ariefzuhri.gamedisc.common.util.*
import com.ariefzuhri.gamedisc.data.source.remote.response.GamesResponse
import com.ariefzuhri.gamedisc.domain.enums.DateFormat
import com.ariefzuhri.gamedisc.domain.model.Game
import javax.inject.Inject

class GameMapper @Inject constructor() : Mapper<GamesResponse.ResultsItem, Game> {

    override fun map(input: GamesResponse.ResultsItem): Game {
        return Game(
            id = input.id.orEmpty(),
            title = input.name.orEmpty() +
                    if (input.released.isNullOrEmpty()) emptyString()
                    else " (${
                        reformatDate(
                            input = input.released,
                            inputFormat = DateFormat.DATE_RAW_LONG,
                            outputFormat = DateFormat.DATE_READABLE_YEAR_ONLY
                        )
                    })",
            genres = input.genres?.joinToString(", ") { it?.name.orEmpty() }.orEmpty(),
            rating = input.rating.decimalPlaces(1),
            posterUrl = input.backgroundImage.orEmpty(),
        )
    }
}