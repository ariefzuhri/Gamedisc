package com.ariefzuhri.gamedisc.data.source.remote

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.ariefzuhri.gamedisc.common.util.getCurrentDate
import com.ariefzuhri.gamedisc.common.util.subtractDate
import com.ariefzuhri.gamedisc.data.source.remote.logger.ErrorLogger
import com.ariefzuhri.gamedisc.data.source.remote.network.ApiService
import com.ariefzuhri.gamedisc.data.source.remote.response.GamesResponse
import com.ariefzuhri.gamedisc.domain.enums.DateFormat
import com.ariefzuhri.gamedisc.domain.enums.Platform
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LatestReleasedGamesPagingSource @Inject constructor(
    private val apiService: ApiService,
    private val remoteErrorLogger: ErrorLogger,
) : RxPagingSource<Int, GamesResponse.ResultsItem>() {

    companion object {
        private val TAG = LatestReleasedGamesPagingSource::class.java.simpleName
        private const val START_PAGE_NUMBER = 1

        private val platformIds = listOf(Platform.PC)
        private val lastDate = getCurrentDate(DateFormat.DATE_RAW_LONG)
        private val startDate = subtractDate(lastDate, DateFormat.DATE_RAW_LONG, 6)
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, GamesResponse.ResultsItem>> {
        val nextPageNumber = params.key ?: START_PAGE_NUMBER
        return apiService.getLatestReleasedGames(
            page = nextPageNumber,
            pageSize = params.loadSize,
            platformIds = platformIds.joinToString(",") { it.id.toString() },
            dateRanges = "$startDate,$lastDate"
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { response ->
                if (response.results != null) {
                    @Suppress("UNCHECKED_CAST")
                    LoadResult.Page(
                        data = response.results as List<GamesResponse.ResultsItem>,
                        prevKey = null,
                        nextKey = if (response.next == null || nextPageNumber == 3) null else nextPageNumber + 1
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