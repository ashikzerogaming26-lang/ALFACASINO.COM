package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DemoRepository
import com.example.model.UserProfile
import com.example.ui.theme.*

@Composable
fun DepositDemoScreen(
    userProfile: UserProfile,
    onSuccessDeposit: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedAmount by remember { mutableDoubleStateOf(5000.0) }
    var selectedMethod by remember { mutableStateOf("Virtual UPI / Fast Pay") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val presetAmounts = listOf(1000.0, 2500.0, 5000.0, 10000.0, 25000.0, 50000.0)
    val methods = listOf(
        "Virtual UPI / Fast Pay" to Icons.Default.FlashOn,
        "Sandbox Credit Card" to Icons.Default.CreditCard,
        "Crypto Demo (USDT-Test)" to Icons.Default.CurrencyBitcoin,
        "Virtual Net Banking" to Icons.Default.AccountBalance
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBg)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Header title
        Text(
            text = "DEMO BALANCE TOP-UP",
            color = GoldPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = "Reload virtual test credits to practice games without risk",
            color = TextMuted,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Current Balance Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Current Demo Balance",
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = DemoRepository.formatCurrency(userProfile.demoBalance),
                        color = TextWhite,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(GoldGradient, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBalanceWallet,
                        contentDescription = null,
                        tint = DarkBg,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Preset Amounts Grid
        Text(
            text = "SELECT DEMO AMOUNT",
            color = TextLight,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            presetAmounts.chunked(3).forEach { rowAmounts ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rowAmounts.forEach { amount ->
                        val isSelected = selectedAmount == amount
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) GoldPrimary else DarkSurfaceElevated)
                                .border(
                                    1.dp,
                                    if (isSelected) GoldSecondary else DarkCardBorder,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { selectedAmount = amount }
                                .padding(horizontal = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+$${amount.toInt()}",
                                color = if (isSelected) DarkBg else TextWhite,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Simulated Method Options
        Text(
            text = "SELECT SIMULATED CHANNEL",
            color = TextLight,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        methods.forEach { (methodName, icon) ->
            val isSelected = selectedMethod == methodName
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { selectedMethod = methodName },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceElevated),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isSelected) GoldPrimary else DarkCardBorder
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .background(
                                color = if (isSelected) GoldPrimary.copy(alpha = 0.2f) else DarkCardBg,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = if (isSelected) GoldPrimary else TextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = methodName,
                        color = if (isSelected) TextWhite else TextLight,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )

                    RadioButton(
                        selected = isSelected,
                        onClick = { selectedMethod = methodName },
                        colors = RadioButtonDefaults.colors(selectedColor = GoldPrimary)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Important Demo Disclaimer Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x22FFC727), RoundedCornerShape(10.dp))
                .border(1.dp, GoldPrimary.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = null,
                    tint = GoldPrimary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "SAFE DEMO ENVIRONMENT: This action immediately injects free virtual testing tokens into your sandbox session. No real banking credentials or financial transactions are involved.",
                    color = GoldSecondary,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Confirm Button
        Button(
            onClick = {
                DemoRepository.depositDemo(selectedAmount, selectedMethod)
                onSuccessDeposit(selectedAmount)
                showSuccessDialog = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("submit_deposit_button"),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GoldPrimary,
                contentColor = DarkBg
            )
        ) {
            Text(
                text = "RELOAD +$${selectedAmount.toInt()} DEMO CREDITS",
                color = DarkBg,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black
            )
        }

        Spacer(modifier = Modifier.height(80.dp))
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = {
                Text(
                    text = "Demo Credits Added!",
                    color = GoldPrimary,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "Successfully loaded ${DemoRepository.formatCurrency(selectedAmount)} demo credits to your balance via $selectedMethod.\n\nEnjoy testing all games completely risk-free!",
                    color = TextLight,
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = { showSuccessDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                ) {
                    Text(text = "Awesome", color = DarkBg, fontWeight = FontWeight.Bold)
                }
            },
            containerColor = DarkSurface
        )
    }
}
