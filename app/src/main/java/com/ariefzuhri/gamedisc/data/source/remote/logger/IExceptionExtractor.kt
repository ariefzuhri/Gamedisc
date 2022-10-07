package com.ariefzuhri.gamedisc.data.source.remote.logger

import retrofit2.HttpException

interface IExceptionExtractor<ErrorResponse> {

    fun getMessage(t: Throwable?): String

    fun deserializeErrorBody(e: HttpException?): ErrorResponse?
}