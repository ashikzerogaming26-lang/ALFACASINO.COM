package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GameItem

@Composable
fun OriginalGameArtView(
    game: GameItem,
    modifier: Modifier = Modifier
) {
    val primaryColor = Color(game.themeColorHex)
    val darkGradient = Brush.radialGradient(
        colors = listOf(
            primaryColor.copy(alpha = 0.55f),
            Color(0xFF141724),
            Color(0xFF090A0F)
        ),
        radius = 450f
    )

    val (icon, subtitle) = when (game.id) {
        "fortune_dragon" -> Icons.Default.LocalFireDepartment to "IMPERIAL DRAGON"
        "fortune_rabbit" -> Icons.Default.ElectricBolt to "CYBER FRENZY"
        "clover_coins" -> Icons.Default.Spa to "LUCKY POT"
        "777_classic" -> Icons.Default.Star to "VEGAS REELS"
        "jackpot_joker" -> Icons.Default.TheaterComedy to "WILD JESTER"
        "money_pot" -> Icons.Default.AccountBalance to "GOLD CAULDRON"
        "777_coins" -> Icons.Default.MonetizationOn to "TRIPLE SEVEN"
        "lucky_neko" -> Icons.Default.Pets to "MANEKI NEKO"
        "live_baccarat" -> Icons.Default.Style to "VIP BACCARAT"
        "velvet_roulette" -> Icons.Default.DonutLarge to "ROULETTE 3D"
        "dragon_hunter" -> Icons.Default.GpsFixed to "LASER CANNON"
        "mega_ace" -> Icons.Default.FilterVintage to "ROYAL ACE"
        "texas_holdem_pro" -> Icons.Default.ContentCopy to "NO-LIMIT HOLD'EM"
        "champions_derby" -> Icons.Default.Speed to "THOROUGHBRED 3D"
        "virtual_soccer" -> Icons.Default.SportsSoccer to "PREMIER LEAGUE"
        "7_up_7_down" -> Icons.Default.Casino to "DICE CHALLENGE"
        "lucky_6_megaball" -> Icons.Default.Adjust to "LIVE LOTTO 6"
        "cyber_strike" -> Icons.Default.SportsEsports to "PRO TOURNAMENT"
        "moba_showdown" -> Icons.Default.Shield to "5V5 SHOWDOWN"
        else -> Icons.Default.SportsEsports to "DELUXE EDITION"
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(darkGradient),
        contentAlignment = Alignment.Center
    ) {
        // Decorative geometric canvas patterns
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            drawCircle(
                color = primaryColor.copy(alpha = 0.15f),
                radius = size.width * 0.45f,
                center = center
            )
            drawCircle(
                color = primaryColor.copy(alpha = 0.25f),
                radius = size.width * 0.32f,
                center = center
            )
            // Accent cross lines
            drawLine(
                color = primaryColor.copy(alpha = 0.2f),
                start = Offset(0f, 0f),
                end = Offset(size.width, size.height),
                strokeWidth = 1.5f
            )
            drawLine(
                color = primaryColor.copy(alpha = 0.2f),
                start = Offset(size.width, 0f),
                end = Offset(0f, size.height),
                strokeWidth = 1.5f
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .background(
                        color = Color(0x33000000),
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = primaryColor,
                    modifier = Modifier.size(34.dp)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = subtitle,
                color = primaryColor,
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
        }
    }
}
