package com.ariefzuhri.gamedisc.common.base

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

open class BaseFragment : Fragment() {

    fun Toolbar.init() {
        setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}