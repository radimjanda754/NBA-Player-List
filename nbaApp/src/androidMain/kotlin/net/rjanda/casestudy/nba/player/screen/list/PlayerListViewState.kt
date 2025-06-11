package net.rjanda.casestudy.nba.player.screen.list

import net.rjanda.casestudy.nba.player.model.data.Player

data class PlayerListViewState(
    val playerList: List<Player>,
    val hasNextPage: Boolean,
)