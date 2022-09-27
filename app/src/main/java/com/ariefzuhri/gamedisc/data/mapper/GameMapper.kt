package com.ariefzuhri.gamedisc.data.mapper

import com.ariefzuhri.gamedisc.common.util.orEmpty
import com.ariefzuhri.gamedisc.common.util.orZero
import com.ariefzuhri.gamedisc.common.util.reformatDate
import com.ariefzuhri.gamedisc.data.source.remote.response.GamesResponse
import com.ariefzuhri.gamedisc.domain.enums.DateFormat
import com.ariefzuhri.gamedisc.domain.model.Game

class GameMapper : Mapper<GamesResponse.ResultsItem, Game> {

    override fun map(input: GamesResponse.ResultsItem): Game {
        return Game(
            id = input.id.orEmpty(),
            name = "${input.name.orEmpty()} (${
                reformatDate(
                    input = input.released.orEmpty(),
                    inputFormat = DateFormat.DATE_RAW_LONG,
                    outputFormat = DateFormat.DATE_READABLE_YEAR_ONLY
                )
            })",
            genres = input.genres?.joinToString(", ").orEmpty(),
            rating = input.rating.orZero(),
            posterUrl = input.backgroundImage.orEmpty(),
        )
    }
}