package com.ariefzuhri.gamedisc.common.util

fun String?.getHostName(): String {
    return this?.split("/")?.get(2).orEmpty()
}

fun emptyString(): String {
    return ""
}