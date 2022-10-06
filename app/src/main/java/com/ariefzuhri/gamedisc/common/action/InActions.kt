package com.ariefzuhri.gamedisc.common.action

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.ariefzuhri.gamedisc.R
import com.ariefzuhri.gamedisc.common.util.getBitmapFromDrawable
import com.ariefzuhri.gamedisc.ui.home.HomeFragment
import com.ariefzuhri.gamedisc.ui.home.HomeFragmentDirections

fun Context?.openTab(url: String?) {
    this?.let { context ->
        val colorScheme = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(ContextCompat.getColor(context, R.color.black))
            .build()

        CustomTabsIntent.Builder().apply {
            setShowTitle(true)
            setDefaultColorSchemeParams(colorScheme)
            getBitmapFromDrawable(R.drawable.ic_back_24)?.let { icon -> setCloseButtonIcon(icon) }
            build().launchUrl(context, Uri.parse(url))
        }
    }
}

fun Context?.openDetailsTab(gameId: Int) {
    val baseUrl = "https://rawg.io/games/"
    openTab("$baseUrl$gameId")
}

fun HomeFragment.navigateToSearch() {
    val action = HomeFragmentDirections.actionHomeToSearch()
    findNavController().navigate(action)
}
