package net.rjanda.casestudy.nba.player.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import net.rjanda.casestudy.nba.player.model.data.Player

/**
 * Interface for managing player storage locally.
 */
interface PlayerStorage {

    /**
     * Replaces the current list of players in storage with the provided list.
     *
     * @param players The list of players to store.
     */
    suspend fun setPlayers(players: List<Player>)

    /**
     * Adds new players to the existing list in storage. Only new players with unique IDs will be added.
     *
     * @param newPlayers The list of players to add.
     */
    suspend fun addPlayers(newPlayers: List<Player>)

    /**
     * Observes a specific player by its unique ID, providing updates whenever
     * the player's data changes.
     *
     * @param playerleId The unique ID of the player to observe.
     * @return A flow emitting the player.
     */
    fun observePlayerById(playerleId: Int): Flow<Player>

    /**
     * Observes the list of all players stored locally, providing updates whenever the data changes.
     * Emits nothing if the list is empty.
     *
     * @return A flow emitting the current list of players.
     */
    fun observePlayers(): Flow<List<Player>>

}

/**
 * Very simple implementation of in memory storage for [PlayerStorage]
 */
class InMemoryPlayerStorage : PlayerStorage {
    private val mStoredPlayers = MutableStateFlow(emptyList<Player>())

    override suspend fun setPlayers(players: List<Player>) {
        mStoredPlayers.value = players
    }

    override suspend fun addPlayers(newPlayers: List<Player>) {
        // Do not add players with IDs that already exist in storage. This can happen due to pagination or multiple fetches.
        val existingIds = mStoredPlayers.value.map { it.id }.toSet()
        val uniqueNewPlayers = newPlayers.filter { it.id !in existingIds }
        mStoredPlayers.value += uniqueNewPlayers
    }

    override fun observePlayerById(playerleId: Int): Flow<Player> {
        return mStoredPlayers.mapNotNull { players ->
            players.find { it.id == playerleId }
        }
    }

    override fun observePlayers(): Flow<List<Player>> = mStoredPlayers.filter { it.isNotEmpty() }

}
