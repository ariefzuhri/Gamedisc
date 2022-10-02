package com.ariefzuhri.gamedisc.common.util

import android.content.Context
import android.widget.Toast

fun Context?.showToast(message: Any?) {
    Toast.makeText(this?.applicationContext, message.toString(), Toast.LENGTH_LONG).show()
}