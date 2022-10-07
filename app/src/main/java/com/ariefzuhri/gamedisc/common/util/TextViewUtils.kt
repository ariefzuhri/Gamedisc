package com.ariefzuhri.gamedisc.common.util

import android.widget.TextView

fun TextView.setDrawableStart(drawableRes: Int) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(drawableRes, 0, 0, 0)
}