package com.raunits.wallpapermanager.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.raunits.wallpapermanager.api.GetCategoriesCallback
import com.raunits.wallpapermanager.api.getCategories
import com.raunits.wallpapermanager.store.StoreUtils
import kotlinx.coroutines.launch
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Categories(navController: NavHostController) {
    val (categories, setCategories) = rememberSaveable { mutableStateOf(listOf<String>()) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreUtils(context)
    val cachedCategories = dataStore.getCategories.collectAsState(initial = "")

    LaunchedEffect(Unit) {
        if (cachedCategories.value!!.isEmpty()) getCategories(object : GetCategoriesCallback {
            override fun onSuccess(_categories: List<String>) {
                setCategories(_categories)
                scope.launch {
                    dataStore.saveCategories(_categories)
                }
            }

            override fun onFailure(error: IOException) {

            }
        })
        else {
            println("USING CACHED CATEGORIES")
            val cats = cachedCategories.value!!.split("^")
            setCategories(cats)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            categories.forEach {
                item {
                    Card(
                        shape = RoundedCornerShape(8.dp), modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        onClick = {
                            navController.navigate("categories/$it")
                        }
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = it,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}