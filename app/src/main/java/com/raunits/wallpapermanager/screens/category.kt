package com.raunits.wallpapermanager.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.raunits.wallpapermanager.ImageGrid
import com.raunits.wallpapermanager.api.GetWallpapersByCategoryCallback
import com.raunits.wallpapermanager.api.getWallpapersByCategory
import com.raunits.wallpapermanager.store.StoreUtils
import com.raunits.wallpapermanager.wallpapers
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Base64

@Composable
fun Category(navController: NavHostController, category: String) {
    val (walls, setWalls) = remember { mutableStateOf(listOf<String>()) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreUtils(context)
    val cachedWalls = dataStore.getCachedCategoryWallpapers(category).collectAsState(initial = "")

    LaunchedEffect(Unit) {
        if (category.isNullOrBlank()) {
            println("BLANK")
            return@LaunchedEffect;
        }

        if (cachedWalls.value!!.isNotEmpty()) {
            println("USING CACHED WALLPAPERS FOR $category")
            val _walls = cachedWalls.value!!.split("^")
            setWalls(_walls)
            return@LaunchedEffect
        }

        println("ARGUMENT: $category")
        getWallpapersByCategory(category, object : GetWallpapersByCategoryCallback {
            override fun onSuccess(images: List<String>) {
                println(images)
                setWalls(images)
                scope.launch {
                    dataStore.saveCategoryWallpapers(category = category, wallpapers = images)
                }
            }

            override fun onFailure(error: IOException) {
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = PaddingValues(top = 0.dp, start = 16.dp, end = 16.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = category, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        ImageGrid(images = walls, onClick = {
            val encodedString: String = Base64.getEncoder().encodeToString(it.toByteArray())
            navController.navigate("wallpapers/$encodedString")
        })
    }
}