package com.vyankateshodilwar.quoteshub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vyankateshodilwar.quoteshub.screens.QuoteDetail
import com.vyankateshodilwar.quoteshub.screens.QuoteListScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main){
                    DataManager.loadAssetsFromFile(applicationContext)
                }
            }
            App()

        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewFuncion() {
}

@Composable
fun App() {
    if (DataManager.isDataLoaded.value) {
        if(DataManager.currentPage.value==Pages.LISTING){
            QuoteListScreen(data = DataManager.data) {
                DataManager.switchPages(it)
            }
        }else{
            DataManager.currentQuote?.let { QuoteDetail(quote = it) }
        }

    }else{
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(1f)
        ) {
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.h6
            )
        }
    }
}

enum class Pages{
    LISTING,
    DETAIL
}
