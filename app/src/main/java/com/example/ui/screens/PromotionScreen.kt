package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.DemoRepository
import com.example.model.PromoItem
import com.example.model.UserProfile
import com.example.ui.theme.*

@Composable
fun PromotionScreen(
    userProfile: UserProfile,
    promos: List<PromoItem>,
    modifier: Modifier = Modifier
) {
    var claimedMessage by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBg),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Screen Header
            Column {
                Text(
                    text = "EXCLUSIVE DEMO REWARDS",
                    color = GoldPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "Boost your trial session with VIP simulation vouchers and free test tokens",
                    color = TextMuted,
                    fontSize = 12.sp
                )
            }
        }

        // Hero Giant Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(DarkCardBg)
                    .border(1.5.dp, GoldPrimary.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.banner_promo_one),
                    contentDescription = "VIP Demo Bonus",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Scrim
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xF0090A0F), Color(0x99090A0F), Color.Transparent)
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .background(GoldGradient, RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "GRAND DEMO JACKPOT",
                            color = DarkBg,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "VIP TIER 3 WELCOME GIFT",
                        color = TextWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Claim your exclusive $10,000 complimentary trial balance immediately.",
                        color = GoldSecondary,
                        fontSize = 11.sp,
                        modifier = Modifier.widthIn(max = 240.dp)
                    )
                }
            }
        }

        if (claimedMessage != null) {
            item {
                AnimatedVisibility(visible = true) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(AccentGreen.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                            .border(1.dp, AccentGreen, RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = claimedMessage ?: "",
                            color = AccentGreen,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Demo Promo List
        item {
            Text(
                text = "AVAILABLE DEMO OFFERS",
                color = TextLight,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }

        items(promos) { promo ->
            PromoCard(
                promo = promo,
                onClaim = {
                    val reward = DemoRepository.claimPromo(promo.id)
                    if (reward > 0) {
                        claimedMessage = "🎉 Successfully claimed ${DemoRepository.formatCurrency(reward)} Demo Credits!"
                    }
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}

@Composable
private fun PromoCard(
    promo: PromoItem,
    onClaim: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (promo.isClaimed) DarkCardBorder else GoldPrimary.copy(alpha = 0.35f)
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = if (promo.isClaimed) Color(0x33FFFFFF) else GoldPrimary,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = promo.tag,
                        color = DarkBg,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "Expires: ${promo.expiresIn}",
                        color = TextMuted,
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = promo.title,
                color = TextWhite,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = promo.subtitle,
                color = TextLight.copy(alpha = 0.8f),
                fontSize = 11.sp,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Demo Reward",
                        color = TextMuted,
                        fontSize = 10.sp
                    )
                    Text(
                        text = "+${DemoRepository.formatCurrency(promo.rewardAmount)}",
                        color = GoldPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Button(
                    onClick = onClaim,
                    enabled = !promo.isClaimed,
                    modifier = Modifier.testTag("claim_promo_${promo.id}"),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldPrimary,
                        disabledContainerColor = DarkSurfaceElevated
                    )
                ) {
                    Text(
                        text = if (promo.isClaimed) "CLAIMED" else "CLAIM DEMO",
                        color = if (promo.isClaimed) TextMuted else DarkBg,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
}
