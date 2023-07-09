package com.raunits.wallpapermanager

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.List
import androidx.compose.material.icons.twotone.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavButton (
    val route: String,
    val title: String,
    val icon: ImageVector
        ) {
    object Wallpapers: NavButton (route = "wallpapers", title = "Wallpapers", icon = Icons.TwoTone.Home)
    object Categories: NavButton (route = "categories", title = "Categories", icon = Icons.TwoTone.List)
    object Favourites: NavButton (route = "favourites", title = "Favourites", icon = Icons.TwoTone.Star)
}