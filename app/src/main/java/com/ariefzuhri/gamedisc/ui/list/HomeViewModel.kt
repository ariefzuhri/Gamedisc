package com.ariefzuhri.gamedisc.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.ariefzuhri.gamedisc.domain.model.Game
import com.ariefzuhri.gamedisc.domain.usecase.SeeLatestReleasedGamesInteractor
import com.ariefzuhri.gamedisc.domain.usecase.SeeTopRatedGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    seeTopRatedGamesUseCase: SeeTopRatedGamesUseCase,
    seeLatestReleasedGamesInteractor: SeeLatestReleasedGamesInteractor,
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val topRatedGames: Flowable<PagingData<Game>> =
        seeTopRatedGamesUseCase.getTopRatedGames().cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    val latestReleasedGames: Flowable<PagingData<Game>> =
        seeLatestReleasedGamesInteractor.getLatestReleasedGames().cachedIn(viewModelScope)
}