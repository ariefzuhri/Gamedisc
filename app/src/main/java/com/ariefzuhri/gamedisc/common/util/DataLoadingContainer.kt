package com.ariefzuhri.gamedisc.common.util

import android.view.View
import com.ariefzuhri.gamedisc.common.ui.custom.StateView
import com.facebook.shimmer.ShimmerFrameLayout

class DataLoadingContainer constructor(
    private val shimmer: ShimmerFrameLayout?,
    private val initState: View?,
    private val emptyState: View?,
    private val errorState: StateView?,
    private vararg val mainContents: View?,
) {

    init {
        initState?.visible()
        shimmer?.gone(true)
        shimmer?.stopShimmer()
        emptyState?.gone(true)
        errorState?.gone(true)
        for (content in mainContents) content.gone(true)
    }

    fun startLoading() {
        initState?.gone(true)
        shimmer?.startShimmer()
        shimmer?.visible()
        emptyState?.gone(true)
        errorState?.gone(true)
        for (content in mainContents) content.gone(true)
    }

    fun stopLoading(isEmpty: Boolean) {
        shimmer?.gone(true)
        shimmer?.stopShimmer()
        if (isEmpty) emptyState?.visible()
        else for (content in mainContents) content.visible()
    }

    fun stopLoading(errorMessage: String?) {
        shimmer?.gone(true)
        shimmer?.stopShimmer()
        errorState?.setTitle(errorMessage)
        errorState?.visible()
    }
}