package net.rjanda.casestudy.nba.player.screen.team

import kotlinx.coroutines.flow.mapNotNull
import net.rjanda.casestudy.nba.core.BaseViewModel
import net.rjanda.casestudy.nba.player.model.data.Player
import net.rjanda.casestudy.nba.player.repository.PlayerRepository

class PlayerTeamViewModel(
    private val playerId: Int,
    private val playerRepository: PlayerRepository
) : BaseViewModel<Player.Team>() {

    init {
        playerRepository.observePlayerById(playerId).mapNotNull { it.team }.observeInState()
    }

}
