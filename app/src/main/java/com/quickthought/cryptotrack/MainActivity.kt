package com.quickthought.cryptotrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.MobileAds
import com.quickthought.cryptotrack.presentation.Screen
import com.quickthought.cryptotrack.presentation.coin_detail.CoinDetailScreen
import com.quickthought.cryptotrack.presentation.coin_list.CoinListScreen
import com.quickthought.cryptotrack.ui.theme.CryptoTrackTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        CoroutineScope(Dispatchers.IO).launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(this@MainActivity) {}
        }

        setContent {
            CryptoTrackTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    // Remove activity-level top bar so individual screens can provide their own app bars
                    topBar = {},
                    contentWindowInsets = WindowInsets(0.dp),
                ) { innerPadding ->
                    val navController = rememberNavController()
                    Column(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .safeDrawingPadding(),
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.CoinListScreen.route,
                            modifier = Modifier.padding(innerPadding),
                        ) {
                            composable(Screen.CoinListScreen.route) {
                                CoinListScreen(navController = navController)
                            }
                            composable(Screen.CoinDetailScreen.route + "/{coinId}") {
                                CoinDetailScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
