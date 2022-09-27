package com.ariefzuhri.gamedisc.data.source.remote.logger

interface ErrorLogger {

    fun e(tag: String, message: String?): Exception

    fun e(tag: String, t: Throwable?): Exception
}