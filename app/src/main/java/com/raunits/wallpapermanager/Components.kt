package com.raunits.wallpapermanager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.raunits.wallpapermanager.components.AndroidDownloader
import com.raunits.wallpapermanager.screens.Categories
import com.raunits.wallpapermanager.screens.Category
import com.raunits.wallpapermanager.screens.Favourites
import com.raunits.wallpapermanager.screens.Wallpaper
import com.raunits.wallpapermanager.screens.Wallpapers

@Composable
fun NavContainer(navController: NavHostController, downloader: AndroidDownloader) {
    NavHost(navController = navController, startDestination = "wallpapers") {
        composable(route = NavButton.Wallpapers.route) {
            Wallpapers()
        }

        composable(route = NavButton.Categories.route) {
            Categories(navController)
        }

        composable(route = NavButton.Favourites.route) {
            Favourites()
        }

        composable(
            route = "categories/{category}",
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) {
            Category(
                navController = navController,
                category = it.arguments?.getString("category") ?: "NOT_FOUND"
            )
        }

        composable(
            route = "wallpapers/{wallpaper}",
            arguments = listOf(navArgument("wallpaper") { type = NavType.StringType })
        ) {
            Wallpaper(
                navController = navController,
                downloader = downloader,
                wallpaper = it.arguments?.getString("wallpaper") ?: ""
            )
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = "Papers", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}

@Composable
fun BottomNavbar(
    items: List<NavButton>,
    navController: NavHostController,
    onItemClick: (NavButton) -> Unit
) {
    val stackEntry = navController.currentBackStackEntryAsState();
    NavigationBar(containerColor = MaterialTheme.colorScheme.background, tonalElevation = 8.dp) {
        items.forEach { item ->
            val selected = item.route == stackEntry.value?.destination?.route
            NavigationBarItem(selected = selected, onClick = { onItemClick(item) }, icon = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                    if (selected) Text(text = item.title)
                }
            })
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ImageGrid(images: List<String>, onClick: (String) -> Unit = {}) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        images.forEach {
            item {
                Card(
                    shape = RoundedCornerShape(8.dp), modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    onClick = {onClick(it)}
                ) {
                    GlideImage(
                        model = it,
                        contentDescription = "lolest",
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}