package com.ariefzuhri.gamedisc.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.ariefzuhri.gamedisc.domain.model.Game
import com.ariefzuhri.gamedisc.domain.usecase.SearchGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchGamesUseCase: SearchGamesUseCase,
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun searchGames(query: String): Flowable<PagingData<Game>> {
        return searchGamesUseCase.searchGames(query).cachedIn(viewModelScope)
    }
}