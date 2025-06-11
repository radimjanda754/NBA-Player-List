package net.rjanda.casestudy.nba.player.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlayerDto(
    val id: Int,
    @Json(name = "first_name") val firstName: String?,
    @Json(name = "last_name") val lastName: String?,
    val position: String?,
    val height: String?,
    val weight: String?,
    @Json(name = "jersey_number") val jerseyNumber: String?,
    val college: String?,
    val country: String?,
    val team: TeamDto?
) {
    @JsonClass(generateAdapter = true)
    data class TeamDto(
        val id: Int,
        val conference: String?,
        val division: String?,
        val city: String?,
        val name: String?,
        @Json(name = "full_name") val fullName: String,
        val abbreviation: String?
    )
}
