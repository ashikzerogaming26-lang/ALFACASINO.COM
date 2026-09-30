package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DemoRepository
import com.example.model.GameItem
import com.example.model.GameType
import com.example.model.UserProfile
import com.example.ui.components.OriginalGameArtView
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun GamePlayDemoScreen(
    game: GameItem,
    userProfile: UserProfile,
    isFavorite: Boolean,
    onToggleFavorite: (String) -> Unit,
    onBack: () -> Unit,
    onQuickDeposit: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val coroutineScope = rememberCoroutineScope()
    var currentBet by remember { mutableStateOf(game.minBet) }
    var isSpinning by remember { mutableStateOf(false) }
    var lastWinAmount by remember { mutableStateOf(0.0) }
    var showBigWin by remember { mutableStateOf(false) }
    var autoPlayActive by remember { mutableStateOf(false) }
    var roundsPlayed by remember { mutableIntStateOf(0) }

    // Slot reel states (symbol names/emojis)
    val slotSymbols = listOf("💎", "7️⃣", "👑", "🔔", "💰", "🍀", "🍇")
    var reel1 by remember { mutableStateOf("7️⃣") }
    var reel2 by remember { mutableStateOf("7️⃣") }
    var reel3 by remember { mutableStateOf("7️⃣") }

    // Wheel rotation state
    var wheelAngle by remember { mutableFloatStateOf(0f) }

    fun playRound() {
        if (isSpinning) return
        if (userProfile.demoBalance < currentBet) {
            onQuickDeposit()
            return
        }

        isSpinning = true
        showBigWin = false
        lastWinAmount = 0.0

        coroutineScope.launch {
            if (game.gameType == GameType.WHEEL) {
                // Wheel simulation spin
                val targetSpin = wheelAngle + 720f + Random.nextInt(0, 360)
                val steps = 30
                for (i in 1..steps) {
                    wheelAngle += (targetSpin - wheelAngle) * 0.15f
                    delay(30)
                }
                wheelAngle = targetSpin % 360f

                // Multiplier based on angle segment
                val multipliers = listOf(0.0, 2.0, 5.0, 1.5, 10.0, 0.0, 20.0, 3.0)
                val segIdx = ((wheelAngle / 45f).toInt()) % multipliers.size
                val mult = multipliers[segIdx]
                val win = currentBet * mult
                lastWinAmount = win
                if (win >= currentBet * 5) showBigWin = true
                DemoRepository.recordGameRound(game.id, currentBet, win)
            } else {
                // Reel animation loop
                for (i in 1..10) {
                    reel1 = slotSymbols.random()
                    reel2 = slotSymbols.random()
                    reel3 = slotSymbols.random()
                    delay(70)
                }
                // Determine final outcome
                val outcomeRoll = Random.nextInt(100)
                val winMultiplier: Double
                if (outcomeRoll < 20) {
                    // Big win (3 matching)
                    val winSym = listOf("7️⃣", "💎", "👑").random()
                    reel1 = winSym
                    reel2 = winSym
                    reel3 = winSym
                    winMultiplier = if (winSym == "7️⃣") 25.0 else 10.0
                    showBigWin = true
                } else if (outcomeRoll < 55) {
                    // Regular win (2 matching)
                    val winSym = slotSymbols.random()
                    reel1 = winSym
                    reel2 = winSym
                    reel3 = slotSymbols.filter { it != winSym }.random()
                    winMultiplier = 2.5
                } else {
                    // No win
                    reel1 = "🍇"
                    reel2 = "🍀"
                    reel3 = "🔔"
                    winMultiplier = 0.0
                }

                val win = currentBet * winMultiplier
                lastWinAmount = win
                DemoRepository.recordGameRound(game.id, currentBet, win)
            }

            roundsPlayed++
            isSpinning = false

            if (autoPlayActive && userProfile.demoBalance >= currentBet) {
                delay(1200)
                if (autoPlayActive) playRound()
            } else if (autoPlayActive) {
                autoPlayActive = false
            }
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBg),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkSurface)
                    .statusBarsPadding()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("game_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = TextWhite
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = game.title,
                        color = TextWhite,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${game.provider} • RTP ${game.rtp}",
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                }

                IconButton(
                    onClick = { onToggleFavorite(game.id) },
                    modifier = Modifier.testTag("game_favorite_toggle")
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) AccentRed else TextWhite
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .background(DarkSurfaceElevated, RoundedCornerShape(16.dp))
                        .border(1.dp, GoldPrimary.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBalanceWallet,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = DemoRepository.formatCurrency(userProfile.demoBalance),
                        color = GoldPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DarkBg)
                .verticalScroll(rememberScrollState())
        ) {
            // Demo notice banner
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0x33FFC727))
                    .border(1.dp, GoldPrimary.copy(alpha = 0.2f))
                    .padding(vertical = 4.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = GoldPrimary,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "FREE DEMO SIMULATION • NO REAL MONEY REQUIRED",
                    color = GoldSecondary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Main Interactive Stage
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(DarkSurface)
                    .border(1.5.dp, GoldPrimary.copy(alpha = 0.35f), RoundedCornerShape(18.dp))
                    .padding(14.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Game Artwork Hero Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(DarkCardBg)
                    ) {
                        if (game.drawableRes != null) {
                            Image(
                                painter = painterResource(id = game.drawableRes),
                                contentDescription = game.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            OriginalGameArtView(game = game)
                        }

                        // Gradient overlay with game name
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Color.Transparent, Color(0xDD090A0F))
                                    )
                                )
                                .padding(10.dp),
                            contentAlignment = Alignment.BottomStart
                        ) {
                            Column {
                                Text(
                                    text = game.title.uppercase(),
                                    color = TextWhite,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Text(
                                    text = "Rounds Simulated: $roundsPlayed",
                                    color = GoldPrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // GAME MECHANIC VIEW
                    if (game.gameType == GameType.WHEEL) {
                        // Interactive Wheel Graphic
                        Box(
                            modifier = Modifier
                                .size(170.dp)
                                .clip(CircleShape)
                                .background(DarkSurfaceElevated)
                                .border(3.dp, GoldPrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .rotate(wheelAngle)
                            ) {
                                val colors = listOf(
                                    Color(0xFFFF3366),
                                    Color(0xFF00E5FF),
                                    Color(0xFFFFC727),
                                    Color(0xFF9D4EDD),
                                    Color(0xFF00E676),
                                    Color(0xFFFF6D00),
                                    Color(0xFF2979FF),
                                    Color(0xFFFFD700)
                                )
                                val sweep = 360f / colors.size
                                colors.forEachIndexed { i, c ->
                                    drawArc(
                                        color = c,
                                        startAngle = i * sweep,
                                        sweepAngle = sweep,
                                        useCenter = true
                                    )
                                }
                                drawCircle(color = DarkSurface, radius = size.width * 0.22f)
                            }
                            // Center pointer
                            Icon(
                                imageVector = Icons.Default.Navigation,
                                contentDescription = null,
                                tint = TextWhite,
                                modifier = Modifier
                                    .size(28.dp)
                                    .rotate(180f)
                            )
                        }
                    } else {
                        // 3-Reel Slot Simulator
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(DarkSurfaceElevated, RoundedCornerShape(12.dp))
                                .border(1.dp, DarkCardBorder, RoundedCornerShape(12.dp))
                                .padding(vertical = 16.dp, horizontal = 10.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ReelSlot(symbol = reel1, isSpinning = isSpinning)
                            ReelDivider()
                            ReelSlot(symbol = reel2, isSpinning = isSpinning)
                            ReelDivider()
                            ReelSlot(symbol = reel3, isSpinning = isSpinning)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Win Banner
                    AnimatedVisibility(
                        visible = lastWinAmount > 0,
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (showBigWin) Brush.horizontalGradient(listOf(Color(0xFFFF0055), Color(0xFFFFC727)))
                                    else Brush.horizontalGradient(listOf(Color(0xFF00C853), Color(0xFF64DD17)))
                                )
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (showBigWin) "🎉 BIG WIN! +${DemoRepository.formatCurrency(lastWinAmount)} DEMO 🎉"
                                else "WIN +${DemoRepository.formatCurrency(lastWinAmount)} DEMO",
                                color = DarkBg,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Bet selector chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "DEMO BET:",
                            color = TextMuted,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf(10.0, 50.0, 100.0, 500.0).forEach { amount ->
                                val isSelected = currentBet == amount
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) GoldPrimary else DarkSurfaceElevated)
                                        .border(
                                            1.dp,
                                            if (isSelected) GoldPrimary else DarkCardBorder,
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable { currentBet = amount }
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = "$${amount.toInt()}",
                                        color = if (isSelected) DarkBg else TextLight,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Action buttons: Auto Play + Spin Demo Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Auto-play button
                        OutlinedButton(
                            onClick = {
                                autoPlayActive = !autoPlayActive
                                if (autoPlayActive && !isSpinning) playRound()
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .testTag("auto_play_button"),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(
                                1.5.dp,
                                if (autoPlayActive) AccentGreen else DarkCardBorder
                            ),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (autoPlayActive) AccentGreen.copy(alpha = 0.15f) else DarkSurfaceElevated
                            )
                        ) {
                            Text(
                                text = if (autoPlayActive) "STOP AUTO" else "AUTO PLAY",
                                color = if (autoPlayActive) AccentGreen else TextLight,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Play / Spin Button
                        Button(
                            onClick = { playRound() },
                            enabled = !isSpinning,
                            modifier = Modifier
                                .weight(2f)
                                .height(50.dp)
                                .testTag("play_demo_button"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GoldPrimary,
                                contentColor = DarkBg
                            )
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = DarkBg,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isSpinning) "SPINNING..." else "PLAY DEMO ($${currentBet.toInt()})",
                                    color = DarkBg,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Information & Rules Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "GAME SPECIFICATIONS & RULES",
                        color = GoldPrimary,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = game.description,
                        color = TextLight,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    HorizontalDivider(color = DarkCardBorder)

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        SpecItem(label = "Category", value = game.category.title)
                        SpecItem(label = "Provider", value = game.provider)
                        SpecItem(label = "Return To Player", value = game.rtp)
                        SpecItem(label = "Min/Max Demo Bet", value = "$${game.minBet.toInt()} / $${game.maxBet.toInt()}")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(DarkSurfaceElevated, RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "ℹ️ Certified RNG Demo Engine. All bets, winnings, and balance figures are purely for gameplay preview and testing mechanics. No real currency is accepted or distributed.",
                            color = TextMuted,
                            fontSize = 10.sp,
                            lineHeight = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun ReelSlot(symbol: String, isSpinning: Boolean) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(DarkCardBg)
            .border(1.5.dp, GoldPrimary.copy(alpha = 0.4f), RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = symbol,
            fontSize = 32.sp,
            modifier = Modifier.scale(if (isSpinning) 0.85f else 1.0f)
        )
    }
}

@Composable
private fun ReelDivider() {
    Box(
        modifier = Modifier
            .width(2.dp)
            .height(50.dp)
            .background(DarkCardBorder)
    )
}

@Composable
private fun SpecItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            color = TextMuted,
            fontSize = 9.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            color = TextWhite,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
