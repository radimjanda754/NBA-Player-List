package net.rjanda.casestudy.nba.core.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MetaPageDto(
    @Json(name = "next_cursor") val nextCursor: Int?,
    @Json(name = "per_page") val perPage: Int
)