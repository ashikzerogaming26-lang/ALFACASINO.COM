package com.example.data

import com.example.R
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.util.Locale

object DemoRepository {

    private val initialGames = listOf(
        // CASINO GAMES
        GameItem(
            id = "golden_empire",
            title = "Golden Empire",
            category = GameCategory.CASINO,
            provider = "Apex Studios",
            badge = GameBadge.HOT,
            rating = 4.9f,
            playsCount = "240K",
            drawableRes = R.drawable.thumb_golden_empire,
            themeColorHex = 0xFFFFD700,
            description = "Explore sacred Aztec temples and uncover legendary golden relics with cascading multiplier reels.",
            gameType = GameType.SLOT_5REEL,
            minBet = 20.0,
            maxBet = 2000.0,
            rtp = "97.2%"
        ),
        GameItem(
            id = "crazy_777",
            title = "Crazy 777",
            category = GameCategory.CASINO,
            provider = "Nova Gaming",
            badge = GameBadge.HOT,
            rating = 4.9f,
            playsCount = "310K",
            drawableRes = R.drawable.thumb_crazy_seven,
            themeColorHex = 0xFFFF3366,
            description = "High-energy classic 3-reel slot featuring crazy multiplier reels and blazing neon rewards.",
            gameType = GameType.SLOT_3REEL,
            minBet = 10.0,
            maxBet = 1000.0,
            rtp = "96.9%"
        ),
        GameItem(
            id = "fortune_dragon",
            title = "Fortune Dragon",
            category = GameCategory.CASINO,
            provider = "Dragon Studio",
            badge = GameBadge.TOP,
            rating = 4.8f,
            playsCount = "185K",
            drawableRes = null,
            themeColorHex = 0xFFFF4500,
            description = "Awaken the celestial golden dragon to unleash flaming wild respins and grand jackpots.",
            gameType = GameType.SLOT_5REEL,
            minBet = 25.0,
            maxBet = 2500.0,
            rtp = "96.7%"
        ),
        GameItem(
            id = "fortune_rabbit",
            title = "Fortune Rabbit",
            category = GameCategory.CASINO,
            provider = "Apex Studios",
            badge = GameBadge.NEW,
            rating = 4.7f,
            playsCount = "92K",
            drawableRes = null,
            themeColorHex = 0xFF00E5FF,
            description = "Hop into good fortune with cyber carrots, prize notes, and quick 8-spin frenzy rounds.",
            gameType = GameType.SLOT_3REEL,
            minBet = 10.0,
            maxBet = 800.0,
            rtp = "96.5%"
        ),
        GameItem(
            id = "clover_coins",
            title = "Clover Coins",
            category = GameCategory.CASINO,
            provider = "Emerald Play",
            badge = GameBadge.NONE,
            rating = 4.7f,
            playsCount = "115K",
            drawableRes = null,
            themeColorHex = 0xFF00E676,
            description = "Four-leaf clovers, shimmering pots of gold, and locked coin lock-and-win bonus games.",
            gameType = GameType.SLOT_5REEL,
            minBet = 15.0,
            maxBet = 1500.0,
            rtp = "96.4%"
        ),
        GameItem(
            id = "777_classic",
            title = "777 Classic",
            category = GameCategory.CASINO,
            provider = "Vegas Retro",
            badge = GameBadge.NONE,
            rating = 4.6f,
            playsCount = "140K",
            drawableRes = null,
            themeColorHex = 0xFFFFD700,
            description = "Authentic mechanical bell, bar, and lucky seven slot experience with crisp click sounds.",
            gameType = GameType.SLOT_3REEL,
            minBet = 5.0,
            maxBet = 500.0,
            rtp = "95.8%"
        ),
        GameItem(
            id = "jackpot_joker",
            title = "Jackpot Joker",
            category = GameCategory.CASINO,
            provider = "Nova Gaming",
            badge = GameBadge.VIP,
            rating = 4.8f,
            playsCount = "165K",
            drawableRes = null,
            themeColorHex = 0xFF9D4EDD,
            description = "The purple wild jester awards mystery coin drops and expanding multipliers up to 1000x.",
            gameType = GameType.SLOT_5REEL,
            minBet = 20.0,
            maxBet = 2000.0,
            rtp = "97.0%"
        ),
        GameItem(
            id = "money_pot",
            title = "Money Pot",
            category = GameCategory.CASINO,
            provider = "Gold Rush",
            badge = GameBadge.HOT,
            rating = 4.8f,
            playsCount = "205K",
            drawableRes = null,
            themeColorHex = 0xFFFFC727,
            description = "Fill the molten cauldron with golden ingots to trigger explosive jackpot coin showers.",
            gameType = GameType.SLOT_5REEL,
            minBet = 25.0,
            maxBet = 2500.0,
            rtp = "96.8%"
        ),
        GameItem(
            id = "777_coins",
            title = "777 Coins",
            category = GameCategory.CASINO,
            provider = "Apex Studios",
            badge = GameBadge.NONE,
            rating = 4.7f,
            playsCount = "88K",
            drawableRes = null,
            themeColorHex = 0xFFFFB300,
            description = "Stacked golden bullions and electric bonus re-spins with 3 fixed in-game jackpots.",
            gameType = GameType.SLOT_3REEL,
            minBet = 10.0,
            maxBet = 1000.0,
            rtp = "96.2%"
        ),
        GameItem(
            id = "lucky_neko",
            title = "Lucky Neko",
            category = GameCategory.CASINO,
            provider = "Sakura Play",
            badge = GameBadge.NEW,
            rating = 4.8f,
            playsCount = "175K",
            drawableRes = null,
            themeColorHex = 0xFFFF85A1,
            description = "The beckoning golden cat bestows multiplier cherry blossoms and gigablox symbols.",
            gameType = GameType.SLOT_5REEL,
            minBet = 20.0,
            maxBet = 2000.0,
            rtp = "96.7%"
        ),

        // LIVE DEMO GAMES
        GameItem(
            id = "mega_wheel",
            title = "Mega Wheel",
            category = GameCategory.LIVE,
            provider = "Apex Live",
            badge = GameBadge.HOT,
            rating = 4.9f,
            playsCount = "420K",
            drawableRes = R.drawable.thumb_mega_wheel,
            themeColorHex = 0xFFFF0055,
            description = "Spectacular interactive 54-segment golden wheel host game with up to 500x Mega Multipliers.",
            gameType = GameType.WHEEL,
            minBet = 10.0,
            maxBet = 5000.0,
            rtp = "96.5%"
        ),
        GameItem(
            id = "live_baccarat",
            title = "Apex Baccarat VIP",
            category = GameCategory.LIVE,
            provider = "Apex Live",
            badge = GameBadge.VIP,
            rating = 4.9f,
            playsCount = "290K",
            drawableRes = null,
            themeColorHex = 0xFFD4AF37,
            description = "Streamlined high-limit baccarat table with squeeze cards, dragon bonus, and bead plate charts.",
            gameType = GameType.CARD_BACCARAT,
            minBet = 50.0,
            maxBet = 10000.0,
            rtp = "98.9%"
        ),
        GameItem(
            id = "velvet_roulette",
            title = "Velvet Roulette 3D",
            category = GameCategory.LIVE,
            provider = "Apex Live",
            badge = GameBadge.TOP,
            rating = 4.8f,
            playsCount = "210K",
            drawableRes = null,
            themeColorHex = 0xFF00C853,
            description = "Crystal clear European roulette wheel with racetrack bets, neighbor callouts, and stats.",
            gameType = GameType.ROULETTE,
            minBet = 20.0,
            maxBet = 5000.0,
            rtp = "97.3%"
        ),

        // FISH GAMES
        GameItem(
            id = "jackpot_fishing",
            title = "Jackpot Fishing",
            category = GameCategory.FISH,
            provider = "Ocean Arcade",
            badge = GameBadge.HOT,
            rating = 4.9f,
            playsCount = "380K",
            drawableRes = R.drawable.thumb_jackpot_fish,
            themeColorHex = 0xFF00E5FF,
            description = "Lock onto giant cyber sharks, electric torpedo eels, and sunken pirate treasure chests.",
            gameType = GameType.FISH_ARCADE,
            minBet = 1.0,
            maxBet = 100.0,
            rtp = "97.0%"
        ),
        GameItem(
            id = "dragon_hunter",
            title = "Golden Dragon Hunter",
            category = GameCategory.FISH,
            provider = "Ocean Arcade",
            badge = GameBadge.NEW,
            rating = 4.8f,
            playsCount = "190K",
            drawableRes = null,
            themeColorHex = 0xFFFFD700,
            description = "Aim laser cannons at the imperial golden dragon to unlock explosive screen-clearing waves.",
            gameType = GameType.FISH_ARCADE,
            minBet = 2.0,
            maxBet = 200.0,
            rtp = "96.8%"
        ),

        // POKER GAMES
        GameItem(
            id = "mega_ace",
            title = "Mega Ace",
            category = GameCategory.POKER,
            provider = "Ace Studio",
            badge = GameBadge.HOT,
            rating = 4.9f,
            playsCount = "250K",
            drawableRes = null,
            themeColorHex = 0xFFFF3366,
            description = "Golden poker card elimination simulator where transformed golden cards boost cascading combos.",
            gameType = GameType.SLOT_5REEL,
            minBet = 20.0,
            maxBet = 2000.0,
            rtp = "97.1%"
        ),
        GameItem(
            id = "texas_holdem_pro",
            title = "Texas Hold'em Pro",
            category = GameCategory.POKER,
            provider = "Ace Studio",
            badge = GameBadge.VIP,
            rating = 4.8f,
            playsCount = "340K",
            drawableRes = null,
            themeColorHex = 0xFF2979FF,
            description = "Realistic heads-up poker simulator with intelligent AI betting patterns and hand rankings.",
            gameType = GameType.CARD_BACCARAT,
            minBet = 25.0,
            maxBet = 5000.0,
            rtp = "98.5%"
        ),

        // SPORTS DEMO
        GameItem(
            id = "champions_derby",
            title = "Champions Derby 3D",
            category = GameCategory.SPORTS,
            provider = "Apex Sports",
            badge = GameBadge.NEW,
            rating = 4.7f,
            playsCount = "120K",
            drawableRes = null,
            themeColorHex = 0xFF00E676,
            description = "Photorealistic 3D virtual thoroughbred horse racing with real-time race simulations every 90s.",
            gameType = GameType.WHEEL,
            minBet = 10.0,
            maxBet = 1000.0,
            rtp = "95.5%"
        ),
        GameItem(
            id = "virtual_soccer",
            title = "Virtual Premier Cup",
            category = GameCategory.SPORTS,
            provider = "Apex Sports",
            badge = GameBadge.HOT,
            rating = 4.8f,
            playsCount = "180K",
            drawableRes = null,
            themeColorHex = 0xFF2979FF,
            description = "Fast-paced simulated football league with instant match highlights and statistical odds.",
            gameType = GameType.WHEEL,
            minBet = 10.0,
            maxBet = 2000.0,
            rtp = "95.9%"
        ),

        // LOTTERY
        GameItem(
            id = "7_up_7_down",
            title = "7 Up 7 Down",
            category = GameCategory.LOTTERY,
            provider = "Nova Gaming",
            badge = GameBadge.HOT,
            rating = 4.8f,
            playsCount = "290K",
            drawableRes = null,
            themeColorHex = 0xFFFFC727,
            description = "Classic two-dice guess simulator: Under 7, Lucky 7, or Over 7 with bonus multipliers.",
            gameType = GameType.WHEEL,
            minBet = 10.0,
            maxBet = 3000.0,
            rtp = "96.5%"
        ),
        GameItem(
            id = "lucky_6_megaball",
            title = "Lucky 6 Mega Ball",
            category = GameCategory.LOTTERY,
            provider = "Apex Lotto",
            badge = GameBadge.NEW,
            rating = 4.6f,
            playsCount = "95K",
            drawableRes = null,
            themeColorHex = 0xFF9D4EDD,
            description = "High-speed ball machine lottery simulator with live draws and golden multiplier balls.",
            gameType = GameType.WHEEL,
            minBet = 5.0,
            maxBet = 500.0,
            rtp = "96.0%"
        ),

        // E-SPORTS
        GameItem(
            id = "cyber_strike",
            title = "Cyber Strike Arena",
            category = GameCategory.ESPORTS,
            provider = "Nova Esports",
            badge = GameBadge.HOT,
            rating = 4.9f,
            playsCount = "220K",
            drawableRes = null,
            themeColorHex = 0xFF00E5FF,
            description = "Tactical FPS esports tournament simulator featuring virtual fantasy squads and live stats.",
            gameType = GameType.SLOT_5REEL,
            minBet = 20.0,
            maxBet = 2500.0,
            rtp = "96.5%"
        ),
        GameItem(
            id = "moba_showdown",
            title = "MOBA Legends VR",
            category = GameCategory.ESPORTS,
            provider = "Nova Esports",
            badge = GameBadge.NEW,
            rating = 4.7f,
            playsCount = "130K",
            drawableRes = null,
            themeColorHex = 0xFFFF3366,
            description = "5v5 lane battle simulation with hero draft odds and dragon objective bonus rounds.",
            gameType = GameType.WHEEL,
            minBet = 15.0,
            maxBet = 1500.0,
            rtp = "96.2%"
        )
    )

    private val initialPromos = listOf(
        PromoItem(
            id = "promo_welcome",
            title = "100% DEMO MATCH BONUS",
            subtitle = "Boost your trial account with up to $10,000 in free demo simulation credits.",
            rewardAmount = 10000.0,
            tag = "NEW PLAYER",
            bannerDrawableRes = R.drawable.banner_promo_one,
            isClaimed = false,
            expiresIn = "24h 00m"
        ),
        PromoItem(
            id = "promo_dragon",
            title = "IMPERIAL DRAGON BONUS",
            subtitle = "Claim your exclusive VIP 3 demonstration reward of $5,000 demo credits.",
            rewardAmount = 5000.0,
            tag = "VIP SPECIAL",
            bannerDrawableRes = R.drawable.banner_promo_two,
            isClaimed = false,
            expiresIn = "3 Days"
        ),
        PromoItem(
            id = "promo_daily",
            title = "DAILY CHECK-IN REWARD",
            subtitle = "Collect your daily free-to-play credits to test new slot and live mechanics.",
            rewardAmount = 1000.0,
            tag = "DAILY",
            bannerDrawableRes = null,
            isClaimed = false,
            expiresIn = "Today"
        ),
        PromoItem(
            id = "promo_cashback",
            title = "15% WEEKEND RECOVERY",
            subtitle = "Enjoy risk-free practice with demo insurance on all casino and fish simulators.",
            rewardAmount = 2500.0,
            tag = "WEEKEND",
            bannerDrawableRes = null,
            isClaimed = false,
            expiresIn = "5 Days"
        )
    )

    private val initialTransactions = listOf(
        TransactionRecord(
            id = "TX-90214",
            title = "Demo Welcome Credit",
            type = TransactionType.PROMO_BONUS,
            amount = 10000.00,
            timestamp = "Today, 10:15 AM",
            status = "Success",
            method = "Demo Portal"
        ),
        TransactionRecord(
            id = "TX-89102",
            title = "Crazy 777 Big Win",
            type = TransactionType.GAME_WIN,
            amount = 3500.00,
            timestamp = "Yesterday, 08:42 PM",
            status = "Completed",
            method = "Slot Simulator"
        ),
        TransactionRecord(
            id = "TX-87421",
            title = "Simulated Fast Deposit",
            type = TransactionType.DEPOSIT_DEMO,
            amount = 5000.00,
            timestamp = "Sep 28, 02:19 PM",
            status = "Demo Success",
            method = "Virtual UPI"
        ),
        TransactionRecord(
            id = "TX-86510",
            title = "Mega Wheel Payout",
            type = TransactionType.GAME_WIN,
            amount = 12000.00,
            timestamp = "Sep 27, 09:30 PM",
            status = "Completed",
            method = "Live Wheel"
        ),
        TransactionRecord(
            id = "TX-85219",
            title = "Test Payout Simulation",
            type = TransactionType.WITHDRAW_DEMO,
            amount = 2000.00,
            timestamp = "Sep 26, 11:05 AM",
            status = "Demo Processed",
            method = "Virtual Bank"
        )
    )

    private val _games = MutableStateFlow(initialGames)
    val games: StateFlow<List<GameItem>> = _games.asStateFlow()

    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _promos = MutableStateFlow(initialPromos)
    val promos: StateFlow<List<PromoItem>> = _promos.asStateFlow()

    private val _transactions = MutableStateFlow(initialTransactions)
    val transactions: StateFlow<List<TransactionRecord>> = _transactions.asStateFlow()

    private val _favorites = MutableStateFlow(setOf("golden_empire", "mega_wheel", "crazy_777"))
    val favorites: StateFlow<Set<String>> = _favorites.asStateFlow()

    fun toggleFavorite(gameId: String) {
        _favorites.update { current ->
            if (current.contains(gameId)) current - gameId else current + gameId
        }
    }

    fun isFavorite(gameId: String): Boolean {
        return _favorites.value.contains(gameId)
    }

    fun depositDemo(amount: Double, method: String): Boolean {
        if (amount <= 0) return false
        _userProfile.update { it.copy(demoBalance = it.demoBalance + amount) }
        val newTx = TransactionRecord(
            id = "TX-" + (10000..99999).random(),
            title = "Simulated Demo Deposit",
            type = TransactionType.DEPOSIT_DEMO,
            amount = amount,
            timestamp = "Just now",
            status = "Demo Success",
            method = method
        )
        _transactions.update { listOf(newTx) + it }
        return true
    }

    fun withdrawDemo(amount: Double, method: String): Boolean {
        if (amount <= 0 || _userProfile.value.demoBalance < amount) return false
        _userProfile.update { it.copy(demoBalance = it.demoBalance - amount) }
        val newTx = TransactionRecord(
            id = "TX-" + (10000..99999).random(),
            title = "Simulated Demo Payout",
            type = TransactionType.WITHDRAW_DEMO,
            amount = amount,
            timestamp = "Just now",
            status = "Demo Processed",
            method = method
        )
        _transactions.update { listOf(newTx) + it }
        return true
    }

    fun claimPromo(promoId: String): Double {
        val promo = _promos.value.find { it.id == promoId } ?: return 0.0
        if (promo.isClaimed) return 0.0

        _promos.update { list ->
            list.map { if (it.id == promoId) it.copy(isClaimed = true) else it }
        }
        val reward = promo.rewardAmount
        _userProfile.update { it.copy(demoBalance = it.demoBalance + reward) }
        val newTx = TransactionRecord(
            id = "TX-" + (10000..99999).random(),
            title = promo.title,
            type = TransactionType.PROMO_BONUS,
            amount = reward,
            timestamp = "Just now",
            status = "Claimed",
            method = "Promotion"
        )
        _transactions.update { listOf(newTx) + it }
        return reward
    }

    fun recordGameRound(gameId: String, betAmount: Double, winAmount: Double) {
        val game = _games.value.find { it.id == gameId }
        val net = winAmount - betAmount
        _userProfile.update {
            it.copy(
                demoBalance = (it.demoBalance + net).coerceAtLeast(0.0),
                totalPlayed = it.totalPlayed + betAmount,
                totalWon = if (winAmount > 0) it.totalWon + winAmount else it.totalWon
            )
        }
        if (winAmount > 0) {
            val newTx = TransactionRecord(
                id = "TX-" + (10000..99999).random(),
                title = "${game?.title ?: "Demo Game"} Win",
                type = TransactionType.GAME_WIN,
                amount = winAmount,
                timestamp = "Just now",
                status = "Completed",
                method = "Simulator"
            )
            _transactions.update { (listOf(newTx) + it).take(30) }
        }
    }

    fun resetDemoBalance() {
        _userProfile.update {
            it.copy(
                demoBalance = 25000.0,
                totalWon = 0.0,
                totalPlayed = 0.0
            )
        }
        val newTx = TransactionRecord(
            id = "TX-" + (10000..99999).random(),
            title = "Demo Balance Reset",
            type = TransactionType.DEPOSIT_DEMO,
            amount = 25000.0,
            timestamp = "Just now",
            status = "Reset",
            method = "System"
        )
        _transactions.update { listOf(newTx) + it }
    }

    fun formatCurrency(amount: Double): String {
        val formatter = NumberFormat.getNumberInstance(Locale.US)
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2
        return "$" + formatter.format(amount)
    }
}
