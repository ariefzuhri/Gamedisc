package com.ariefzuhri.gamedisc.common.base

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ariefzuhri.gamedisc.common.util.showToast

open class BaseFragment : Fragment() {

    fun Toolbar?.init() {
        this?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    fun showToast(message: String?) {
        context.showToast(message)
    }
}