package com.ariefzuhri.gamedisc.di

import com.ariefzuhri.gamedisc.data.mapper.GameMapper
import com.ariefzuhri.gamedisc.data.mapper.Mapper
import com.ariefzuhri.gamedisc.data.repository.GameRepository
import com.ariefzuhri.gamedisc.data.source.remote.response.GamesResponse
import com.ariefzuhri.gamedisc.domain.model.Game
import com.ariefzuhri.gamedisc.domain.repository.IGameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class])
@InstallIn(value = [SingletonComponent::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideGameRepository(
        gameRepository: GameRepository
    ): IGameRepository

    @Binds
    abstract fun provideGameMapper(
        gameMapper: GameMapper
    ): Mapper<GamesResponse.ResultsItem, Game>
}