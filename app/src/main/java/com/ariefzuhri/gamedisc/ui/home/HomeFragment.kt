package com.ariefzuhri.gamedisc.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefzuhri.gamedisc.R
import com.ariefzuhri.gamedisc.common.action.navigateToSearch
import com.ariefzuhri.gamedisc.common.action.openDetailsTab
import com.ariefzuhri.gamedisc.common.base.BaseFragment
import com.ariefzuhri.gamedisc.common.ui.adapter.GameAdapter
import com.ariefzuhri.gamedisc.common.ui.adapter.HeaderAdapter
import com.ariefzuhri.gamedisc.common.ui.adapter.HorizontalWrapperAdapter
import com.ariefzuhri.gamedisc.common.util.DataLoadingContainer
import com.ariefzuhri.gamedisc.databinding.FragmentHomeBinding
import com.ariefzuhri.gamedisc.domain.enums.ViewOrientation
import com.ariefzuhri.gamedisc.domain.model.Game
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private val disposable = CompositeDisposable()

    private lateinit var topRatedGameHeaderAdapter: HeaderAdapter
    private lateinit var topRatedGameAdapter: GameAdapter

    private lateinit var latestReleasedGameHeaderAdapter: HeaderAdapter
    private lateinit var latestReleasedGameAdapter: GameAdapter

    private lateinit var gameLoadingContainer: DataLoadingContainer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initGameLoadingContainer()
        initTopRatedGameAdapter()
        initLatestReleasedGameAdapter()
        initGameRecyclerView()

        observeTopRatedGames()
        observeLatestReleasedGames()

        initClickListeners()
    }

    private fun initGameLoadingContainer() {
        gameLoadingContainer = DataLoadingContainer(
            shimmer = binding.lytGamePlaceholder.root,
            initState = null,
            emptyState = null,
            errorState = binding.stateError,
            binding.rvGame
        )
    }

    private fun initTopRatedGameAdapter() {
        topRatedGameHeaderAdapter = HeaderAdapter(
            title = getString(R.string.tv_title_top_rated_games_home),
            iconRes = R.drawable.ic_top_rated_24_home
        )

        topRatedGameAdapter = GameAdapter(ViewOrientation.HORIZONTAL).apply {
            setEventListener(object : GameAdapter.EventListener {
                override fun onItemClick(game: Game) {
                    context.openDetailsTab(game.id)
                }
            })

            addLoadStateListener { loadState ->
                with(gameLoadingContainer) {
                    when (val currentState = loadState.refresh) {
                        is LoadState.Loading -> startLoading()
                        is LoadState.Error -> stopLoading(currentState.error.message)
                        is LoadState.NotLoading -> stopLoading(false)
                    }
                }
            }
        }
    }

    private fun initLatestReleasedGameAdapter() {
        latestReleasedGameHeaderAdapter = HeaderAdapter(
            title = getString(R.string.tv_title_latest_released_games_home),
            iconRes = R.drawable.ic_latest_released_24_home
        )

        latestReleasedGameAdapter = GameAdapter(ViewOrientation.HORIZONTAL).apply {
            setEventListener(object : GameAdapter.EventListener {
                override fun onItemClick(game: Game) {
                    context.openDetailsTab(game.id)
                }
            })

            addLoadStateListener { loadState ->
                with(gameLoadingContainer) {
                    when (val currentState = loadState.refresh) {
                        is LoadState.Loading -> startLoading()
                        is LoadState.Error -> stopLoading(currentState.error.message)
                        is LoadState.NotLoading -> stopLoading(false)
                    }
                }
            }
        }
    }

    private fun initGameRecyclerView() {
        val concatAdapter = ConcatAdapter(
            topRatedGameHeaderAdapter,
            HorizontalWrapperAdapter(topRatedGameAdapter),
            latestReleasedGameHeaderAdapter,
            HorizontalWrapperAdapter(latestReleasedGameAdapter)
        )

        binding.rvGame.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            adapter = concatAdapter
        }
    }

    private fun observeTopRatedGames() {
        disposable.add(
            viewModel.topRatedGames.subscribe { pagingData ->
                topRatedGameAdapter.submitData(lifecycle, pagingData)
            }
        )
    }

    private fun observeLatestReleasedGames() {
        disposable.add(
            viewModel.latestReleasedGames.subscribe { pagingData ->
                latestReleasedGameAdapter.submitData(lifecycle, pagingData)
            }
        )
    }

    private fun initClickListeners() {
        binding.apply {
            edtSearch.setOnClickListener {
                navigateToSearch()
            }
            stateError.setRetryAction {
                topRatedGameAdapter.retry()
                latestReleasedGameAdapter.retry()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        disposable.dispose()
    }
}