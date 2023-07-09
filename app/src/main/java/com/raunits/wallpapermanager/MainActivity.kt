package com.raunits.wallpapermanager

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.raunits.wallpapermanager.components.AndroidDownloader
import com.raunits.wallpapermanager.ui.theme.WallpaperManagerTheme
import java.util.prefs.Preferences

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val downloader = AndroidDownloader(this)
        super.onCreate(savedInstanceState)
        setContent {
            WallpaperManagerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EntryPoint(downloader)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPoint(downloader: AndroidDownloader) {
    val navController = rememberNavController();
    Scaffold(
        bottomBar = {
            BottomNavbar(
                items = listOf(NavButton.Wallpapers, NavButton.Categories, NavButton.Favourites),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route)
                })
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar()
            NavContainer(navController = navController, downloader = downloader)
        }

    }
}