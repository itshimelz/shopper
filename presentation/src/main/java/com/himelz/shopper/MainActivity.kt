package com.himelz.shopper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.himelz.shopper.navigation.BottomNavigationBar
import com.himelz.shopper.navigation.Destinations
import com.himelz.shopper.ui.feature.cart.CartScreen
import com.himelz.shopper.ui.feature.details.DetailsScreen
import com.himelz.shopper.ui.feature.home.HomeScreen
import com.himelz.shopper.ui.feature.profile.ProfileScreen
import com.himelz.shopper.ui.theme.ShopperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopperTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        MainNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Destinations.HomeScreen) {
        composable<Destinations.HomeScreen> {
            HomeScreen(navController)
        }

        composable<Destinations.DetailsScreen> {
            DetailsScreen(navController)
        }

        composable<Destinations.CartScreen> {
            CartScreen(navController)
        }

        composable<Destinations.ProfileScreen> {
            ProfileScreen(navController)
        }
    }
}