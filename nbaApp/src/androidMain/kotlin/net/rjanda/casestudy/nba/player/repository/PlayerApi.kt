package net.rjanda.casestudy.nba.player.repository

import net.rjanda.casestudy.nba.player.model.response.PlayerListDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface for fetching players from the Web API.
 */
interface PlayerApi {

    /**
     * Fetches a list of players from the web API.
     *
     * @param cursor Optional cursor for paginating results (start of the next page).
     * @param perPage Optional number of items per page.
     * @return [PlayerListDto] object containing the list of players and pagination metadata.
     */
    @GET("players/")
    suspend fun getPlayers(
        @Query("cursor") cursor: Int? = null,
        @Query("per_page") perPage: Int? = 35
    ): PlayerListDto

}
