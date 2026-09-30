package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
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
import com.example.model.PromoItem
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun PromoBannerCarousel(
    promos: List<PromoItem>,
    onPromoClick: (PromoItem) -> Unit,
    modifier: Modifier = Modifier
) {
    if (promos.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = { promos.size })

    // Auto-advance banner every 4 seconds
    LaunchedEffect(pagerState) {
        while (true) {
            delay(4000)
            if (promos.size > 1) {
                val nextPage = (pagerState.currentPage + 1) % promos.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("promo_banner_carousel")
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
        ) { page ->
            val promo = promos[page]
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(DarkCardBg)
                    .clickable { onPromoClick(promo) }
            ) {
                // Banner Graphic
                if (promo.bannerDrawableRes != null) {
                    Image(
                        painter = painterResource(id = promo.bannerDrawableRes),
                        contentDescription = promo.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF2B1055), Color(0xFF7597DE))
                                )
                            )
                    )
                }

                // Dark gradient scrim for strong text readability
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xEE090A0F),
                                    Color(0x88090A0F),
                                    Color.Transparent
                                )
                            )
                        )
                )

                // Banner Copy & CTA
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    // Tag
                    Box(
                        modifier = Modifier
                            .background(GoldGradient, RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = promo.tag,
                            color = DarkBg,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.5.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = promo.title,
                        color = TextWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = promo.subtitle,
                        color = TextLight.copy(alpha = 0.85f),
                        fontSize = 10.sp,
                        maxLines = 2,
                        modifier = Modifier.widthIn(max = 220.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Small CTA Pill
                    Row(
                        modifier = Modifier
                            .background(
                                color = if (promo.isClaimed) Color(0x66FFFFFF) else GoldPrimary,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (promo.isClaimed) "CLAIMED" else "CLAIM DEMO",
                            color = DarkBg,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Pager indicators
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(promos.size) { index ->
                val isCurrent = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .height(4.dp)
                        .width(if (isCurrent) 16.dp else 4.dp)
                        .clip(CircleShape)
                        .background(if (isCurrent) GoldPrimary else TextDim)
                )
            }
        }
    }
}
