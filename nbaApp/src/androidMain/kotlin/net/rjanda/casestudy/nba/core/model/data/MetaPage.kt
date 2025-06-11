package net.rjanda.casestudy.nba.core.model.data

import net.rjanda.casestudy.nba.core.model.response.MetaPageDto

/**
 * Data class representing pagination metadata from the API response.
 *
 * @property nextCursor The cursor value for loading the next page.
 * @property perPage The number of items per page.
 */
data class MetaPage(
    val nextCursor: Int?,
    val perPage: Int
) {
    constructor(response: MetaPageDto) : this(
        nextCursor = response.nextCursor,
        perPage = response.perPage
    )
}