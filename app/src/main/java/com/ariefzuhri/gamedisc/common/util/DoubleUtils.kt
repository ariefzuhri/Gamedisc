package com.ariefzuhri.gamedisc.common.util

import java.text.DecimalFormat

fun Double?.decimalPlaces(digits: Int): Double {
    return DecimalFormat("#.${"#".repeat(digits)}").format(this.orZero()).toDouble()
}