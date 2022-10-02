package com.ariefzuhri.gamedisc.domain.model

data class Game(
    val id: Int,
    val title: String,
    val genres: String,
    val rating: Double,
    val posterUrl: String,
)