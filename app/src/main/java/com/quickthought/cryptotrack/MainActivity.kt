package com.quickthought.cryptotrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.quickthought.cryptotrack.presentation.Screen
import com.quickthought.cryptotrack.presentation.coin_detail.CoinDetailScreen
import com.quickthought.cryptotrack.presentation.coin_list.CoinListScreen
import com.quickthought.cryptotrack.ui.theme.CryptoTrackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            CryptoTrackTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(R.string.app_name)) },
                        )
                    }
                ) { innerPadding ->
                    val navController = rememberNavController()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .safeDrawingPadding()
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.CoinListScreen.route,
                            modifier = Modifier.padding(innerPadding)
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