package com.ariefzuhri.gamedisc.data.source.remote

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.ariefzuhri.gamedisc.data.source.remote.logger.ErrorLogger
import com.ariefzuhri.gamedisc.data.source.remote.network.ApiService
import com.ariefzuhri.gamedisc.data.source.remote.response.GamesResponse
import com.ariefzuhri.gamedisc.domain.enums.Platform
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopRatedGamesPagingSource @Inject constructor(
    private val apiService: ApiService,
    private val remoteErrorLogger: ErrorLogger,
) : RxPagingSource<Int, GamesResponse.ResultsItem>() {

    private var platformIds = listOf<Platform>()

    fun setParams(platformIds: List<Platform>) {
        this.platformIds = platformIds
    }

    companion object {
        private val TAG = TopRatedGamesPagingSource::class.java.simpleName
        private const val START_PAGE_NUMBER = 1
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, GamesResponse.ResultsItem>> {
        val nextPageNumber = params.key ?: START_PAGE_NUMBER
        return apiService.getTopRatedGames(
            page = nextPageNumber,
            pageSize = params.loadSize,
            platformIds = platformIds.joinToString(",") { it.id.toString() }
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { response ->
                if (response.results != null) {
                    @Suppress("UNCHECKED_CAST")
                    LoadResult.Page(
                        data = response.results as List<GamesResponse.ResultsItem>,
                        prevKey = null,
                        nextKey = if (response.next == null) null else nextPageNumber + 1
                    )
                } else {
                    LoadResult.Error(remoteErrorLogger.e(TAG, response.error))
                }
            }.onErrorReturn { error ->
                LoadResult.Error(remoteErrorLogger.e(TAG, error))
            }
    }

    override fun getRefreshKey(state: PagingState<Int, GamesResponse.ResultsItem>): Int? {
        return null
    }
}