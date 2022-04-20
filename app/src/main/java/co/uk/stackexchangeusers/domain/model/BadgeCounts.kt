package co.uk.stackexchangeusers.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BadgeCounts(
    val bronze: Int,
    val gold: Int,
    val silver: Int
)