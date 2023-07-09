package com.raunits.wallpapermanager.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raunits.wallpapermanager.ImageGrid
import com.raunits.wallpapermanager.wallpapers

@Composable
fun Wallpapers() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = PaddingValues(top = 16.dp, start = 8.dp, end = 8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageGrid(images = wallpapers)
    }
}