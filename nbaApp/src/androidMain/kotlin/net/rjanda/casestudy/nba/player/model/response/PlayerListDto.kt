package net.rjanda.casestudy.nba.player.model.response

import com.squareup.moshi.JsonClass
import net.rjanda.casestudy.nba.core.model.response.MetaPageDto

@JsonClass(generateAdapter = true)
data class PlayerListDto(
    val data: List<PlayerDto>,
    val meta: MetaPageDto?,
)