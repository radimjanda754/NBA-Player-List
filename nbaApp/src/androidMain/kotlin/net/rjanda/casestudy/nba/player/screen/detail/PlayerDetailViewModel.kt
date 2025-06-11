package net.rjanda.casestudy.nba.player.screen.detail

import net.rjanda.casestudy.nba.core.BaseViewModel
import net.rjanda.casestudy.nba.player.model.data.Player
import net.rjanda.casestudy.nba.player.repository.PlayerRepository

class PlayerDetailViewModel(
    private val playerId: Int,
    private val playerRepository: PlayerRepository
) : BaseViewModel<Player>() {

    init {
        playerRepository.observePlayerById(playerId).observeInState()
    }

}
