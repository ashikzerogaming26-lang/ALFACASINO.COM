package com.example.model

enum class GameCategory(val id: String, val title: String, val badge: String = "") {
    CASINO("casino", "Casino", "HOT"),
    LIVE("live", "Live", "LIVE"),
    FISH("fish", "Fish", ""),
    POKER("poker", "Poker", ""),
    SPORTS("sports", "Sports", "SOON"),
    LOTTERY("lottery", "Lottery", ""),
    ESPORTS("esports", "E-sports", "")
}

enum class GameBadge {
    NONE, HOT, NEW, VIP, TOP
}

enum class GameType {
    SLOT_3REEL,
    SLOT_5REEL,
    WHEEL,
    FISH_ARCADE,
    CARD_BACCARAT,
    ROULETTE
}

data class GameItem(
    val id: String,
    val title: String,
    val category: GameCategory,
    val provider: String,
    val badge: GameBadge = GameBadge.NONE,
    val rating: Float = 4.8f,
    val playsCount: String = "128K",
    val drawableRes: Int? = null,
    val themeColorHex: Long = 0xFFFFD700,
    val description: String = "",
    val gameType: GameType = GameType.SLOT_3REEL,
    val minBet: Double = 10.0,
    val maxBet: Double = 1000.0,
    val rtp: String = "96.8%",
    val isFavorite: Boolean = false
)

data class UserProfile(
    val username: String = "Player888",
    val vipLevel: Int = 3,
    val vipTitle: String = "Gold Elite",
    val demoBalance: Double = 25480.00,
    val totalWon: Double = 84200.00,
    val totalPlayed: Double = 112000.00,
    val unreadNotifications: Int = 2
)

enum class TransactionType {
    DEPOSIT_DEMO,
    WITHDRAW_DEMO,
    PROMO_BONUS,
    GAME_WIN,
    GAME_BET
}

data class TransactionRecord(
    val id: String,
    val title: String,
    val type: TransactionType,
    val amount: Double,
    val timestamp: String,
    val status: String = "Completed",
    val method: String = "Instant Demo"
)

data class PromoItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val rewardAmount: Double,
    val tag: String,
    val bannerDrawableRes: Int? = null,
    val isClaimed: Boolean = false,
    val expiresIn: String = "12h 45m"
)
