package com.ariefzuhri.gamedisc.di

import com.ariefzuhri.gamedisc.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(value = [SingletonComponent::class])
abstract class UseCaseModule {

    @Singleton
    @Binds
    abstract fun provideSearchGamesUseCase(
        searchGamesInteractor: SearchGamesInteractor
    ): SearchGamesUseCase

    @Singleton
    @Binds
    abstract fun provideSeeLatestReleasedGamesUseCase(
        seeLatestReleasedGamesInteractor: SeeLatestReleasedGamesInteractor
    ): SeeLatestReleasedGamesUseCase

    @Singleton
    @Binds
    abstract fun provideSeeTopRatedGamesUseCase(
        seeTopRatedGamesInteractor: SeeTopRatedGamesInteractor
    ): SeeTopRatedGamesUseCase
}