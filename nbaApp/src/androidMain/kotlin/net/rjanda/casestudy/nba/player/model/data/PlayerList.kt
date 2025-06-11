package net.rjanda.casestudy.nba.player.model.data

import net.rjanda.casestudy.nba.core.model.data.MetaPage
import net.rjanda.casestudy.nba.player.model.response.PlayerListDto

data class PlayerList(
    val data: List<Player>,
    val meta: MetaPage?,
) {
    constructor(response: PlayerListDto) : this(
        data = response.data.map { Player(it) },
        meta = response.meta?.let { MetaPage(it) }
    )
}