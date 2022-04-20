package co.uk.stackexchangeusers.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(

    @Json(name = "account_id") val  accountId: Int?,
    @Json(name = "badge_counts") val badgeCounts: BadgeCounts?,
    @Json(name = "creation_date") val creationDate: Int?,
    @Json(name = "display_name") val displayName: String?,
    @Json(name = "location") val location: String?,
    @Json(name = "profile_image") val profileImage: String?,
    @Json(name = "user_id") val userId: Int?,
    @Json(name = "accept_rate") val acceptRate: Int?,
    @Json(name = "reputation") val reputation: Int?,

)