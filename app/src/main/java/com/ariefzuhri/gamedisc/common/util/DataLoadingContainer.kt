package com.ariefzuhri.gamedisc.common.util

import android.view.View
import com.facebook.shimmer.ShimmerFrameLayout

class DataLoadingContainer constructor(
    private val shimmer: ShimmerFrameLayout?,
    private val emptyState: View?,
    private vararg val mainContents: View?,
) {

    init {
        startLoading()
    }

    fun startLoading() {
        shimmer?.startShimmer()
        shimmer?.visible()
        emptyState?.gone(true)
        for (content in mainContents) content.gone(true)
    }

    fun stopLoading(isEmpty: Boolean) {
        shimmer?.gone(true)
        shimmer?.stopShimmer()
        if (isEmpty) emptyState?.visible()
        else for (content in mainContents) content.visible()
    }
}