package com.ariefzuhri.gamedisc.data.source.remote.logger

import android.util.Log
import com.ariefzuhri.gamedisc.data.source.remote.response.ErrorResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteErrorLogger @Inject constructor(
    private val exceptionExtractor: IExceptionExtractor<ErrorResponse>,
) : ErrorLogger {

    override fun e(tag: String, message: String?): Exception {
        Log.e(tag, message.orEmpty())
        return Exception(message)
    }

    override fun e(tag: String, t: Throwable?): Exception {
        Log.e(tag, t?.stackTraceToString().orEmpty())
        val message = exceptionExtractor.getMessage(t)
        return Exception(message)
    }
}