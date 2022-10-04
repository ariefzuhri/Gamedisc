package com.ariefzuhri.gamedisc.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.ariefzuhri.gamedisc.common.base.BaseFragment
import com.ariefzuhri.gamedisc.common.ui.adapter.GameAdapter
import com.ariefzuhri.gamedisc.common.util.DataLoadingContainer
import com.ariefzuhri.gamedisc.common.util.gone
import com.ariefzuhri.gamedisc.common.util.showKeyboard
import com.ariefzuhri.gamedisc.databinding.FragmentSearchBinding
import com.ariefzuhri.gamedisc.domain.enums.ViewOrientation
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

        initView()
    }

    private fun initResultsLoadingContainer() {
        resultsLoadingContainer = DataLoadingContainer(
            shimmer = binding.lytResultsPlaceholder.root,
            emptyState = binding.lytEmptyResults,
            binding.rvResults
        )
    }

    private fun initResultsAdapter() {
        resultsAdapter = GameAdapter(ViewOrientation.VERTICAL).apply {
            addLoadStateListener { loadState ->
                when (val currentState = loadState.refresh) {
                    is LoadState.Loading -> {
                        binding.lytHintSearch.gone(true)
                        resultsLoadingContainer.startLoading()
                    }
                    is LoadState.Error -> showToast(currentState.error.message)
                    is LoadState.NotLoading -> {
                        resultsLoadingContainer.stopLoading(isEmpty = itemCount == 0)
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

    private fun initView() {
        binding.apply {
            tbSearch.init()
            svGame.showKeyboard()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        disposable.dispose()
    }
}