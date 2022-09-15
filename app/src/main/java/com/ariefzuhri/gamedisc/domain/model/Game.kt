package com.ariefzuhri.gamedisc.domain.model

data class Game(
    val id: Int,
    val name: String,
    val genres: String,
    val rating: Double,
    val posterUrl: String,
)