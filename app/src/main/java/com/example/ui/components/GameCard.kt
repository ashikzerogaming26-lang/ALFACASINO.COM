package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GameBadge
import com.example.model.GameItem
import com.example.ui.theme.*

@Composable
fun GameCard(
    game: GameItem,
    isFavorite: Boolean,
    onToggleFavorite: (String) -> Unit,
    onClick: (GameItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1.0f,
        label = "card_scale"
    )

    Column(
        modifier = modifier
            .scale(scale)
            .testTag("game_card_${game.id}")
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onTap = {
                        onClick(game)
                    }
                )
            }
    ) {
        // Thumbnail Container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.0f)
                .clip(RoundedCornerShape(12.dp))
                .background(DarkCardBg)
                .border(
                    width = 1.dp,
                    color = if (isFavorite) GoldPrimary.copy(alpha = 0.5f) else DarkCardBorder,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            // Artwork
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

            // Bottom gradient overlay for readability
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0xCC090A0F))
                        )
                    )
            )

            // Badge (HOT / NEW / VIP / TOP)
            if (game.badge != GameBadge.NONE) {
                val (badgeColor, badgeText) = when (game.badge) {
                    GameBadge.HOT -> AccentRed to "HOT"
                    GameBadge.NEW -> AccentGreen to "NEW"
                    GameBadge.VIP -> AccentPurple to "VIP"
                    GameBadge.TOP -> GoldPrimary to "TOP"
                    else -> Color.Transparent to ""
                }
                Box(
                    modifier = Modifier
                        .padding(start = 6.dp, top = 6.dp)
                        .align(Alignment.TopStart)
                        .background(
                            color = badgeColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = badgeText,
                        color = if (game.badge == GameBadge.TOP) DarkBg else TextWhite,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            // Favorite Button (Top-Right)
            Box(
                modifier = Modifier
                    .padding(end = 6.dp, top = 6.dp)
                    .align(Alignment.TopEnd)
                    .size(28.dp)
                    .background(
                        color = Color(0x77000000),
                        shape = CircleShape
                    )
                    .clickable {
                        onToggleFavorite(game.id)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite ${game.title}",
                    tint = if (isFavorite) AccentRed else TextLight,
                    modifier = Modifier.size(16.dp)
                )
            }

            // Small Live/RTP tag at bottom of thumbnail
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 6.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = GoldPrimary,
                    modifier = Modifier.size(10.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "${game.rating}",
                    color = GoldSecondary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "• ${game.playsCount}",
                    color = TextMuted,
                    fontSize = 9.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Title & Provider
        Text(
            text = game.title,
            color = TextWhite,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = game.provider,
            color = TextMuted,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
