package net.rjanda.casestudy.nba.player.model.data

import net.rjanda.casestudy.nba.player.model.response.PlayerDto

data class Player(
    val id: Int,
    val firstName: String?,
    val lastName: String?,
    val fullName: String,
    val position: String?,
    val height: String?,
    val weight: String?,
    val jerseyNumber: String?,
    val college: String?,
    val country: String?,
    val team: Team?
) {
    constructor(response: PlayerDto) : this(
        id = response.id,
        firstName = response.firstName,
        lastName = response.lastName,
        fullName = "${response.firstName ?: ""} ${response.lastName ?: ""}",
        position = response.position,
        height = response.height,
        weight = response.weight,
        jerseyNumber = response.jerseyNumber,
        college = response.college,
        country = response.country,
        team = response.team?.let { Team(it) }
    )

    data class Team(
        val id: Int,
        val conference: String?,
        val division: String?,
        val city: String?,
        val name: String?,
        val fullName: String,
        val abbreviation: String?
    ) {
        constructor(response: PlayerDto.TeamDto) : this(
            id = response.id,
            conference = response.conference,
            division = response.division,
            city = response.city,
            name = response.name,
            fullName = response.fullName,
            abbreviation = response.abbreviation
        )
    }
}
