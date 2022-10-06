package com.ariefzuhri.gamedisc.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.ariefzuhri.gamedisc.common.action.openDetailsTab
import com.ariefzuhri.gamedisc.common.base.BaseFragment
import com.ariefzuhri.gamedisc.common.ui.adapter.GameAdapter
import com.ariefzuhri.gamedisc.common.util.DataLoadingContainer
import com.ariefzuhri.gamedisc.common.util.showKeyboard
import com.ariefzuhri.gamedisc.databinding.FragmentSearchBinding
import com.ariefzuhri.gamedisc.domain.enums.ViewOrientation
import com.ariefzuhri.gamedisc.domain.model.Game
import com.jakewharton.rxbinding4.widget.queryTextChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()

    private val disposable = CompositeDisposable()

    private lateinit var resultsAdapter: GameAdapter
    private lateinit var resultsLoadingContainer: DataLoadingContainer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initResultsLoadingContainer()
        initResultsAdapter()
        initResultsRecyclerView()
        observeResults()

        initViews()
        initClickListeners()
    }

    private fun initResultsLoadingContainer() {
        resultsLoadingContainer = DataLoadingContainer(
            shimmer = binding.lytResultsPlaceholder.root,
            initState = binding.stateInitResults,
            emptyState = binding.stateEmptyResults,
            errorState = binding.stateErrorResults,
            binding.rvResults
        )
    }

    private fun initResultsAdapter() {
        resultsAdapter = GameAdapter(ViewOrientation.VERTICAL).apply {
            setEventListener(object : GameAdapter.EventListener {
                override fun onItemClick(game: Game) {
                    context.openDetailsTab(game.id)
                }
            })

            addLoadStateListener { loadState ->
                with(resultsLoadingContainer) {
                    when (val currentState = loadState.refresh) {
                        is LoadState.Loading -> startLoading()
                        is LoadState.Error -> stopLoading(currentState.error.message)
                        is LoadState.NotLoading -> stopLoading(isEmpty = itemCount == 0)
                    }
                }
            }
        }
    }

    private fun initResultsRecyclerView() {
        binding.rvResults.apply {
            val gridLayoutManager = GridLayoutManager(context, 2)
            layoutManager = gridLayoutManager
            adapter = resultsAdapter
            setHasFixedSize(true)
        }
    }

    private fun observeResults() {
        val queryStream = binding.svGame.queryTextChanges()
            .skipInitialValue()
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged { oldQuery, newQuery ->
                oldQuery.toString().trim() == newQuery.toString().trim()
            }
            .filter { query ->
                query.trim().isNotEmpty()
            }
            .map { query ->
                query.toString()
            }
        disposable.add(queryStream.subscribe { query ->
            viewModel.searchGames(query).subscribe { pagingData ->
                resultsAdapter.submitData(lifecycle, pagingData)
            }
        })
    }

    private fun initViews() {
        binding.apply {
            tbSearch.init()
            svGame.showKeyboard()
        }
    }

    private fun initClickListeners() {
        binding.apply {
            stateErrorResults.setRetryAction {
                resultsAdapter.retry()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        disposable.dispose()
    }
}