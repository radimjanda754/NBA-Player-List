package net.rjanda.casestudy.nba.player.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import net.rjanda.casestudy.nba.core.model.data.ContentState
import net.rjanda.casestudy.nba.core.model.data.MetaPage
import net.rjanda.casestudy.nba.player.model.data.Player
import net.rjanda.casestudy.nba.player.model.data.PlayerList

/**
 * Main interface for managing players.
 */
interface PlayerRepository {
    /**
     * Refreshes the list of players by fetching the latest data from the API
     * and updating the local storage.
     */
    suspend fun refreshPlayers()

    /**
     * Loads the next page of players from the API and appends them to the
     * existing list in the local storage.
     */
    suspend fun loadNextPlayers()

    /**
     * Observes the list of players stored locally, providing updates whenever
     * the data changes. Emits nothing if empty.
     *
     * @return A flow emitting the current list of players.
     */
    fun observePlayers(): Flow<List<Player>>

    /**
     * Observes a specific player by its unique ID, providing updates whenever
     * the player's data changes.
     *
     * @param playerId The unique ID of the player to observe.
     * @return A flow emitting the player.
     */
    fun observePlayerById(playerId: Int): Flow<Player>

    /**
     * Observes the paging state of players, indicating whether more pages
     * are available or if the next page is already loading or empty.
     *
     * @return A flow emitting the current paging state.
     */
    fun observePlayersPaging(): Flow<ContentState<MetaPage>>

}

class LocalPlayerRepository(
    private val playerApi: PlayerApi,
    private val playerStorage: PlayerStorage,
) : PlayerRepository {

    // Basic paging
    private val playerPagingState = MutableStateFlow<ContentState<MetaPage>>(ContentState.Empty)

    override suspend fun refreshPlayers() {
        val data = PlayerList(playerApi.getPlayers())
        playerStorage.setPlayers(data.data)
        updatePlayerPaging(data.meta)
    }

    override suspend fun loadNextPlayers() {
        if (playerPagingState.value is ContentState.Success) {
            val data = (playerPagingState.value as ContentState.Success<MetaPage>).data
            playerPagingState.value = ContentState.Loading
            val newData = PlayerList(playerApi.getPlayers(cursor = data.nextCursor))
            playerStorage.addPlayers(newData.data)
            updatePlayerPaging(newData.meta)
        }
    }

    private fun updatePlayerPaging(next: MetaPage?) {
        if (next?.nextCursor != null) {
            playerPagingState.update { ContentState.Success(next) }
        } else {
            playerPagingState.update { ContentState.Empty }
        }
    }

    override fun observePlayers(): Flow<List<Player>> = playerStorage.observePlayers()

    override fun observePlayerById(playerId: Int): Flow<Player> = playerStorage.observePlayerById(playerId)

    override fun observePlayersPaging(): Flow<ContentState<MetaPage>> = playerPagingState

}
