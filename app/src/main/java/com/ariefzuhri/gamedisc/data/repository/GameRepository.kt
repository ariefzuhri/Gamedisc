package com.ariefzuhri.gamedisc.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava3.flowable
import com.ariefzuhri.gamedisc.data.mapper.Mapper
import com.ariefzuhri.gamedisc.data.source.remote.LatestReleasedGamesPagingSource
import com.ariefzuhri.gamedisc.data.source.remote.SearchGamesPagingSource
import com.ariefzuhri.gamedisc.data.source.remote.TopRatedGamesPagingSource
import com.ariefzuhri.gamedisc.data.source.remote.response.GamesResponse.ResultsItem
import com.ariefzuhri.gamedisc.domain.enums.Platform
import com.ariefzuhri.gamedisc.domain.model.Game
import com.ariefzuhri.gamedisc.domain.repository.IGameRepository
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor(
    private val topRatedGamesPagingSource: TopRatedGamesPagingSource,
    private val latestReleasedGamesPagingSource: LatestReleasedGamesPagingSource,
    private val searchGamesPagingSource: SearchGamesPagingSource,
    private val gameMapper: Mapper<ResultsItem, Game>,
) : IGameRepository {

    override fun getTopRatedGames(
        pageSize: Int,
        platformIds: List<Platform>,
    ): Flowable<PagingData<Game>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                initialLoadSize = pageSize,
                enablePlaceholders = true,
                prefetchDistance = 4
            ),
            pagingSourceFactory = {
                topRatedGamesPagingSource.apply {
                    this.platformIds = platformIds
                }
            }
        ).flowable.map { pagingData ->
            pagingData.map {
                gameMapper.map(it)
            }
        }
    }

    override fun getLatestReleasedGames(
        pageSize: Int,
        platformIds: List<Platform>,
        startDate: String,
        endDate: String,
    ): Flowable<PagingData<Game>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                initialLoadSize = pageSize,
                enablePlaceholders = true,
                prefetchDistance = 4
            ),
            pagingSourceFactory = {
                latestReleasedGamesPagingSource.apply {
                    this.platformIds = platformIds
                    this.startDate = startDate
                    this.endDate = endDate
                }
            }
        ).flowable.map { pagingData ->
            pagingData.map {
                gameMapper.map(it)
            }
        }
    }

    override fun searchGames(
        pageSize: Int,
        platformIds: List<Platform>,
        query: String,
    ): Flowable<PagingData<Game>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                initialLoadSize = pageSize,
                enablePlaceholders = true,
                prefetchDistance = 4
            ),
            pagingSourceFactory = {
                searchGamesPagingSource.apply {
                    this.platformIds = platformIds
                    this.query = query
                }
            }
        ).flowable.map { pagingData ->
            pagingData.map {
                gameMapper.map(it)
            }
        }
    }
}