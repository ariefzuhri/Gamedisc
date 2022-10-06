package com.ariefzuhri.gamedisc.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefzuhri.gamedisc.common.action.navigateToSearch
import com.ariefzuhri.gamedisc.common.action.openDetailsTab
import com.ariefzuhri.gamedisc.common.base.BaseFragment
import com.ariefzuhri.gamedisc.common.ui.adapter.GameAdapter
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

    private lateinit var topRatedGameAdapter: GameAdapter
    private lateinit var latestReleasedGameAdapter: GameAdapter

    private lateinit var topRatedGamesLoadingContainer: DataLoadingContainer
    private lateinit var latestReleasedGamesLoadingContainer: DataLoadingContainer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTopRatedGameLoadingContainer()
        initTopRatedGameAdapter()
        initTopRatedGameRecyclerView()
        observeTopRatedGames()

        initLatestReleasedGameLoadingContainer()
        initLatestReleasedGameAdapter()
        initLatestReleasedGameRecyclerView()
        observeLatestReleasedGames()

        initClickListeners()
    }

    private fun initTopRatedGameLoadingContainer() {
        topRatedGamesLoadingContainer = DataLoadingContainer(
            shimmer = binding.lytTopRatedGamePlaceholder.root,
            initState = null,
            emptyState = null,
            errorState = null,
            binding.rvTopRatedGame
        )
    }

    private fun initTopRatedGameAdapter() {
        topRatedGameAdapter = GameAdapter(ViewOrientation.HORIZONTAL).apply {
            setEventListener(object : GameAdapter.EventListener {
                override fun onItemClick(game: Game) {
                    context.openDetailsTab(game.id)
                }
            })

            addLoadStateListener { loadState ->
                with(topRatedGamesLoadingContainer) {
                    when (val currentState = loadState.refresh) {
                        is LoadState.Loading -> startLoading()
                        is LoadState.Error -> stopLoading(currentState.error.message)
                        is LoadState.NotLoading -> stopLoading(false)
                    }
                }
            }
        }
    }

    private fun initTopRatedGameRecyclerView() {
        binding.rvTopRatedGame.apply {
            val linearLayoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false
            )
            layoutManager = linearLayoutManager
            adapter = topRatedGameAdapter
            setHasFixedSize(true)
        }
    }

    private fun observeTopRatedGames() {
        disposable.add(
            viewModel.topRatedGames.subscribe { pagingData ->
                topRatedGameAdapter.submitData(lifecycle, pagingData)
            }
        )
    }

    private fun initLatestReleasedGameLoadingContainer() {
        latestReleasedGamesLoadingContainer = DataLoadingContainer(
            shimmer = binding.lytLatestReleasedGamePlaceholder.root,
            initState = null,
            emptyState = null,
            errorState = binding.stateError,
            binding.rvLatestReleasedGame
        )
    }

    private fun initLatestReleasedGameAdapter() {
        latestReleasedGameAdapter = GameAdapter(ViewOrientation.VERTICAL).apply {
            setEventListener(object : GameAdapter.EventListener {
                override fun onItemClick(game: Game) {
                    context.openDetailsTab(game.id)
                }
            })

            addLoadStateListener { loadState ->
                with(latestReleasedGamesLoadingContainer) {
                    when (val currentState = loadState.refresh) {
                        is LoadState.Loading -> startLoading()
                        is LoadState.Error -> stopLoading(currentState.error.message)
                        is LoadState.NotLoading -> stopLoading(false)
                    }
                }
            }
        }
    }

    private fun initLatestReleasedGameRecyclerView() {
        binding.rvLatestReleasedGame.apply {
            val gridLayoutManager = GridLayoutManager(context, 2)
            layoutManager = gridLayoutManager
            adapter = latestReleasedGameAdapter
            setHasFixedSize(true)
        }
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