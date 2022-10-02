package com.ariefzuhri.gamedisc.common.util

import android.widget.ImageView
import coil.load
import com.ariefzuhri.gamedisc.R

fun ImageView.load(source: Any?) {
    load(source) {
        placeholder(R.drawable.ic_placeholder_24)
        error(R.drawable.ic_placeholder_24)
    }
}