package com.ariefzuhri.gamedisc.di

import com.ariefzuhri.gamedisc.data.source.remote.logger.ErrorLogger
import com.ariefzuhri.gamedisc.data.source.remote.logger.ExceptionExtractor
import com.ariefzuhri.gamedisc.data.source.remote.logger.IExceptionExtractor
import com.ariefzuhri.gamedisc.data.source.remote.logger.RemoteErrorLogger
import com.ariefzuhri.gamedisc.data.source.remote.response.ErrorResponse
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(value = [SingletonComponent::class])
abstract class LoggerModule {

    @Binds
    abstract fun provideExceptionExtractor(
        exceptionExtractor: ExceptionExtractor,
    ): IExceptionExtractor<ErrorResponse>

    @Binds
    abstract fun provideRemoteLogger(
        remoteLogger: RemoteErrorLogger,
    ): ErrorLogger
}