package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
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
import com.example.model.GameCategory
import com.example.model.GameItem
import com.example.model.PromoItem
import com.example.ui.components.CategoryRail
import com.example.ui.components.GameCard
import com.example.ui.components.PromoBannerCarousel
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    games: List<GameItem>,
    promos: List<PromoItem>,
    favorites: Set<String>,
    onToggleFavorite: (String) -> Unit,
    onGameClick: (GameItem) -> Unit,
    onPromoClick: (PromoItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf(GameCategory.CASINO) }
    var searchQuery by remember { mutableStateOf("") }
    var onlyFavorites by remember { mutableStateOf(false) }

    // Filter games by Category, Search, and Favorites
    val filteredGames = remember(games, selectedCategory, searchQuery, onlyFavorites, favorites) {
        games.filter { game ->
            val matchCategory = if (onlyFavorites) true else game.category == selectedCategory
            val matchSearch = searchQuery.isEmpty() ||
                    game.title.contains(searchQuery, ignoreCase = true) ||
                    game.provider.contains(searchQuery, ignoreCase = true)
            val matchFav = !onlyFavorites || favorites.contains(game.id)
            matchCategory && matchSearch && matchFav
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBg)
    ) {
        // Promotional Carousel at top
        PromoBannerCarousel(
            promos = promos,
            onPromoClick = onPromoClick,
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )

        // Main Content Area: Left Category Rail + Right Game Grid
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            // Left Vertical Category Menu
            CategoryRail(
                selectedCategory = selectedCategory,
                onSelectCategory = { cat ->
                    selectedCategory = cat
                    onlyFavorites = false
                },
                modifier = Modifier.fillMaxHeight()
            )

            // Right Main Game Grid Area
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(DarkBg)
                    .padding(horizontal = 8.dp)
            ) {
                // Category Header & Quick Filter Pills
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (onlyFavorites) "FAVORITES" else selectedCategory.title.uppercase(),
                            color = GoldPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "${filteredGames.size} Games available",
                            color = TextMuted,
                            fontSize = 10.sp
                        )
                    }

                    // Favorites filter toggle chip
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (onlyFavorites) AccentRed.copy(alpha = 0.2f) else DarkSurfaceElevated)
                            .border(
                                1.dp,
                                if (onlyFavorites) AccentRed else DarkCardBorder,
                                RoundedCornerShape(14.dp)
                            )
                            .clickable { onlyFavorites = !onlyFavorites }
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Favorites Filter",
                                tint = if (onlyFavorites) AccentRed else TextMuted,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Favorites",
                                color = if (onlyFavorites) AccentRed else TextLight,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Compact Search Input
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(DarkSurfaceElevated)
                        .border(1.dp, DarkCardBorder, RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = TextMuted,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = {
                                Text(
                                    text = "Search demo titles...",
                                    color = TextDim,
                                    fontSize = 11.sp
                                )
                            },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = GoldPrimary,
                                focusedTextColor = TextWhite,
                                unfocusedTextColor = TextWhite
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("game_search_input")
                        )
                        if (searchQuery.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear",
                                tint = TextMuted,
                                modifier = Modifier
                                    .size(16.dp)
                                    .clickable { searchQuery = "" }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Game Cards Grid
                if (filteredGames.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "No games found in this category",
                                color = TextMuted,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("games_grid"),
                        contentPadding = PaddingValues(bottom = 72.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(filteredGames, key = { it.id }) { game ->
                            GameCard(
                                game = game,
                                isFavorite = favorites.contains(game.id),
                                onToggleFavorite = onToggleFavorite,
                                onClick = onGameClick
                            )
                        }
                    }
                }
            }
        }
    }
}
