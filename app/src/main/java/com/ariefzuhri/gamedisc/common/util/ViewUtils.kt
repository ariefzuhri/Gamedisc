package com.ariefzuhri.gamedisc.common.util

import android.view.View
import android.view.ViewGroup

fun View.fillMaxWidth() {
    layoutParams = layoutParams.apply {
        width = ViewGroup.LayoutParams.MATCH_PARENT
    }
}

fun View?.visible() {
    this?.let {
        visibility = View.VISIBLE
    }
}

fun View?.gone(shouldBeGone: Boolean) {
    this?.let {
        if (shouldBeGone) visibility = View.GONE
        else visible()
    }
}