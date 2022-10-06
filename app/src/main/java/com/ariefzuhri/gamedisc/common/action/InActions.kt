package com.ariefzuhri.gamedisc.common.action

import androidx.navigation.fragment.findNavController
import com.ariefzuhri.gamedisc.ui.home.HomeFragment
import com.ariefzuhri.gamedisc.ui.home.HomeFragmentDirections

fun HomeFragment.navigateToSearch() {
    val action = HomeFragmentDirections.actionHomeToSearch()
    findNavController().navigate(action)
}
