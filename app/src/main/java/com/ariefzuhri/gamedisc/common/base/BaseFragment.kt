package com.ariefzuhri.gamedisc.common.base

import androidx.fragment.app.Fragment
import com.ariefzuhri.gamedisc.common.util.showToast

open class BaseFragment : Fragment() {

    fun showToast(message: String?) {
        context.showToast(message)
    }
}