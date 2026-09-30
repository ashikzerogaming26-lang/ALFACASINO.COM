package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

enum class AppNavTab(val title: String) {
    HOME("Home"),
    DEPOSIT("Deposit Demo"),
    PROMOTION("Promotion"),
    WITHDRAW("Withdraw Demo"),
    MEMBER("Member")
}

@Composable
fun BottomNavBar(
    currentTab: AppNavTab,
    onTabSelected: (AppNavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(DarkSurface)
            .border(width = 1.dp, color = DarkCardBorder)
            .navigationBarsPadding(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // Home
            NavTabItem(
                title = "Home",
                icon = Icons.Default.Home,
                isSelected = currentTab == AppNavTab.HOME,
                onClick = { onTabSelected(AppNavTab.HOME) },
                testTag = "nav_home",
                modifier = Modifier.weight(1f)
            )

            // Deposit Demo
            NavTabItem(
                title = "Deposit",
                icon = Icons.Default.AccountBalanceWallet,
                isSelected = currentTab == AppNavTab.DEPOSIT,
                onClick = { onTabSelected(AppNavTab.DEPOSIT) },
                testTag = "nav_deposit",
                modifier = Modifier.weight(1f)
            )

            // Center Promotion - visually larger elevated button
            Box(
                modifier = Modifier
                    .weight(1.2f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .offset(y = (-10).dp)
                        .clickable { onTabSelected(AppNavTab.PROMOTION) }
                        .testTag("nav_promo_center")
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .shadow(elevation = 10.dp, shape = CircleShape, spotColor = GoldPrimary)
                            .background(GoldGradient, CircleShape)
                            .border(2.dp, GoldSecondary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CardGiftcard,
                            contentDescription = "Promotions",
                            tint = DarkBg,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Promotion",
                        color = if (currentTab == AppNavTab.PROMOTION) GoldPrimary else TextLight,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Withdraw Demo
            NavTabItem(
                title = "Withdraw",
                icon = Icons.Default.Payments,
                isSelected = currentTab == AppNavTab.WITHDRAW,
                onClick = { onTabSelected(AppNavTab.WITHDRAW) },
                testTag = "nav_withdraw",
                modifier = Modifier.weight(1f)
            )

            // Member
            NavTabItem(
                title = "Member",
                icon = Icons.Default.Person,
                isSelected = currentTab == AppNavTab.MEMBER,
                onClick = { onTabSelected(AppNavTab.MEMBER) },
                testTag = "nav_member",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun NavTabItem(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String,
    modifier: Modifier = Modifier
) {
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) GoldPrimary else TextMuted,
        label = "nav_icon_color"
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) GoldPrimary else TextMuted,
        label = "nav_text_color"
    )

    Column(
        modifier = modifier
            .fillMaxHeight()
            .clip(CircleShape)
            .clickable { onClick() }
            .padding(vertical = 6.dp)
            .testTag(testTag),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = iconColor,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = title,
            color = textColor,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}
