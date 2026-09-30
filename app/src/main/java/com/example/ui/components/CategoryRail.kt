package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GameCategory
import com.example.ui.theme.*

@Composable
fun CategoryRail(
    selectedCategory: GameCategory,
    onSelectCategory: (GameCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = GameCategory.values()

    Box(
        modifier = modifier
            .width(76.dp)
            .fillMaxHeight()
            .background(DarkSurface)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(categories) { category ->
                val isSelected = category == selectedCategory
                CategoryRailItem(
                    category = category,
                    isSelected = isSelected,
                    onClick = { onSelectCategory(category) }
                )
            }
        }
    }
}

@Composable
private fun CategoryRailItem(
    category: GameCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) CategoryActiveBg else Color.Transparent,
        label = "cat_bg"
    )
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) GoldPrimary else TextMuted,
        label = "cat_icon"
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) TextWhite else TextMuted,
        label = "cat_text"
    )

    val icon: ImageVector = when (category) {
        GameCategory.CASINO -> Icons.Default.Casino
        GameCategory.LIVE -> Icons.Default.LiveTv
        GameCategory.FISH -> Icons.Default.Waves
        GameCategory.POKER -> Icons.Default.Style
        GameCategory.SPORTS -> Icons.Default.SportsSoccer
        GameCategory.LOTTERY -> Icons.Default.ConfirmationNumber
        GameCategory.ESPORTS -> Icons.Default.SportsEsports
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .clip(RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp))
            .background(bgColor)
            .clickable { onClick() }
            .testTag("category_tab_${category.id}"),
        contentAlignment = Alignment.Center
    ) {
        // Active indicator vertical bar on left edge
        if (isSelected) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(36.dp)
                    .align(Alignment.CenterStart)
                    .background(
                        color = GoldPrimary,
                        shape = RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp)
                    )
            )
        }

        // Tag pill if present
        if (category.badge.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 4.dp, end = 4.dp)
                    .background(
                        color = if (category.badge == "LIVE") AccentRed else GoldDark,
                        shape = RoundedCornerShape(3.dp)
                    )
                    .padding(horizontal = 4.dp, vertical = 1.dp)
            ) {
                Text(
                    text = category.badge,
                    color = TextWhite,
                    fontSize = 7.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(
                        color = if (isSelected) GoldPrimary.copy(alpha = 0.15f) else Color(0x1AFFFFFF),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = category.title,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = category.title,
                color = textColor,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}
