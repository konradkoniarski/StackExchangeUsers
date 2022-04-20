package co.uk.stackexchangeusers.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(

    val has_more: Boolean,
    val items: List<User>,
    val quota_max: Int,
    val quota_remaining: Int
)