package com.raunits.wallpapermanager.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.raunits.wallpapermanager.ImageGrid
import com.raunits.wallpapermanager.components.AndroidDownloader
import com.raunits.wallpapermanager.components.Downloader
import com.raunits.wallpapermanager.wallpapers
import java.util.Base64

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Wallpaper(navController: NavHostController, downloader: AndroidDownloader, wallpaper: String) {
    val decoded: String = String(Base64.getDecoder().decode(wallpaper))
    print("decoded: $decoded")

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            println("STARTING DOWNLOAD: $decoded")
            val filename = decoded.split("/").last()
            downloader.downloadImage(decoded, filename)
        }) {
            Icon(imageVector = Icons.TwoTone.KeyboardArrowDown, contentDescription = "Download wallpaper")
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                model = decoded,
                contentDescription = "lolest",
            )
        }
    }
}