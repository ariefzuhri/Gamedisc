package com.ariefzuhri.gamedisc.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefzuhri.gamedisc.common.base.BaseFragment
import com.ariefzuhri.gamedisc.common.ui.adapter.GameAdapter
import com.ariefzuhri.gamedisc.common.util.DataLoadingContainer
import com.ariefzuhri.gamedisc.databinding.FragmentHomeBinding
import com.ariefzuhri.gamedisc.domain.enums.ViewOrientation
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
    }

    private fun initTopRatedGameLoadingContainer() {
        topRatedGamesLoadingContainer = DataLoadingContainer(
            shimmer = binding.layoutTopRatedGamePlaceholder.root,
            emptyState = null,
            binding.rvTopRatedGame
        )
    }

    private fun initTopRatedGameAdapter() {
        topRatedGameAdapter = GameAdapter(ViewOrientation.HORIZONTAL).apply {
            addLoadStateListener { loadState ->
                when (val currentState = loadState.refresh) {
                    is LoadState.Loading -> topRatedGamesLoadingContainer.startLoading()
                    is LoadState.Error -> showToast(currentState.error.message)
                    is LoadState.NotLoading -> topRatedGamesLoadingContainer.stopLoading(false)
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
            shimmer = binding.layoutLatestReleasedGamePlaceholder.root,
            emptyState = null,
            binding.rvLatestReleasedGame
        )
    }

    private fun initLatestReleasedGameAdapter() {
        latestReleasedGameAdapter = GameAdapter(ViewOrientation.VERTICAL).apply {
            addLoadStateListener { loadState ->
                when (val currentState = loadState.refresh) {
                    is LoadState.Loading -> latestReleasedGamesLoadingContainer.startLoading()
                    is LoadState.Error -> showToast(currentState.error.message)
                    is LoadState.NotLoading -> latestReleasedGamesLoadingContainer.stopLoading(false)
                }
            }
        }
    }

    private fun initLatestReleasedGameRecyclerView() {
        binding.rvLatestReleasedGame.apply {
            val gridLayoutManager = GridLayoutManager(context, 2)
            layoutManager = gridLayoutManager
            adapter = latestReleasedGameAdapter
        }
    }

    private fun observeLatestReleasedGames() {
        disposable.add(
            viewModel.latestReleasedGames.subscribe { pagingData ->
                latestReleasedGameAdapter.submitData(lifecycle, pagingData)
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        disposable.dispose()
    }
}