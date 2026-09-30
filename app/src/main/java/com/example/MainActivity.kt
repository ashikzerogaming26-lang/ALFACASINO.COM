package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.DemoRepository
import com.example.model.GameItem
import com.example.ui.components.AppNavTab
import com.example.ui.components.BottomNavBar
import com.example.ui.components.TopHeaderBar
import com.example.ui.screens.*
import com.example.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppPortal()
            }
        }
    }
}

@Composable
fun MainAppPortal() {
    val games by DemoRepository.games.collectAsStateWithLifecycle()
    val promos by DemoRepository.promos.collectAsStateWithLifecycle()
    val userProfile by DemoRepository.userProfile.collectAsStateWithLifecycle()
    val transactions by DemoRepository.transactions.collectAsStateWithLifecycle()
    val favorites by DemoRepository.favorites.collectAsStateWithLifecycle()

    var currentTab by remember { mutableStateOf(AppNavTab.HOME) }
    var activeDemoGame by remember { mutableStateOf<GameItem?>(null) }
    var showNotificationsDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    // If an active demo game is open, handle Back button to return to catalog
    if (activeDemoGame != null) {
        GamePlayDemoScreen(
            game = activeDemoGame!!,
            userProfile = userProfile,
            isFavorite = favorites.contains(activeDemoGame!!.id),
            onToggleFavorite = { DemoRepository.toggleFavorite(it) },
            onBack = { activeDemoGame = null },
            onQuickDeposit = {
                activeDemoGame = null
                currentTab = AppNavTab.DEPOSIT
            }
        )
    } else {
        // Handle Back button to return to Home tab if on secondary tabs
        if (currentTab != AppNavTab.HOME) {
            BackHandler {
                currentTab = AppNavTab.HOME
            }
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBg),
            topBar = {
                TopHeaderBar(
                    userProfile = userProfile,
                    onDepositClick = { currentTab = AppNavTab.DEPOSIT },
                    onNotificationsClick = { showNotificationsDialog = true },
                    onLogoutClick = { showLogoutDialog = true }
                )
            },
            bottomBar = {
                BottomNavBar(
                    currentTab = currentTab,
                    onTabSelected = { currentTab = it }
                )
            },
            containerColor = DarkBg
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(DarkBg)
            ) {
                when (currentTab) {
                    AppNavTab.HOME -> {
                        HomeScreen(
                            games = games,
                            promos = promos,
                            favorites = favorites,
                            onToggleFavorite = { DemoRepository.toggleFavorite(it) },
                            onGameClick = { game -> activeDemoGame = game },
                            onPromoClick = { currentTab = AppNavTab.PROMOTION }
                        )
                    }

                    AppNavTab.DEPOSIT -> {
                        DepositDemoScreen(
                            userProfile = userProfile,
                            onSuccessDeposit = { currentTab = AppNavTab.HOME }
                        )
                    }

                    AppNavTab.PROMOTION -> {
                        PromotionScreen(
                            userProfile = userProfile,
                            promos = promos
                        )
                    }

                    AppNavTab.WITHDRAW -> {
                        WithdrawDemoScreen(
                            userProfile = userProfile
                        )
                    }

                    AppNavTab.MEMBER -> {
                        MemberScreen(
                            userProfile = userProfile,
                            transactions = transactions,
                            onResetAccount = { DemoRepository.resetDemoBalance() },
                            onNavigateDeposit = { currentTab = AppNavTab.DEPOSIT }
                        )
                    }
                }
            }
        }
    }

    // Notifications Dialog
    if (showNotificationsDialog) {
        AlertDialog(
            onDismissRequest = { showNotificationsDialog = false },
            title = {
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.NotificationsActive,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "System Announcements",
                        color = GoldPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "• Welcome to ApexPlay Demo Portal! Test games with unlimited sandbox credits.",
                        color = TextWhite,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "• New Arrival: Golden Empire 5-Reel slot simulator is now live in Casino.",
                        color = TextLight,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "• Reminder: This application is strictly an offline/free demonstration testbed. No real currency is accepted.",
                        color = GoldSecondary,
                        fontSize = 11.sp
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showNotificationsDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                ) {
                    Text(text = "Understood", color = DarkBg, fontWeight = FontWeight.Bold)
                }
            },
            containerColor = DarkSurface
        )
    }

    // Logout / Power Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = {
                Text(
                    text = "Demo Session Control",
                    color = GoldPrimary,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "Would you like to reset your virtual sandbox balance back to $25,000.00 and restart your test session?",
                    color = TextLight,
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        DemoRepository.resetDemoBalance()
                        showLogoutDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AccentRed)
                ) {
                    Text(text = "Reset Wallet", color = TextWhite, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text(text = "Cancel", color = TextMuted)
                }
            },
            containerColor = DarkSurface
        )
    }
}
