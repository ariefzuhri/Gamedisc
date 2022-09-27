package com.ariefzuhri.gamedisc.common.util

fun Double?.orZero(): Double {
    return this ?: 0.0
}

fun Int?.orEmpty(): Int {
    return this ?: -1
}