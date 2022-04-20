package co.uk.stackexchangeusers.service

import co.uk.stackexchangeusers.domain.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("users")
    suspend fun getUsers(
        @Query("order") order: String? = "desc",
        @Query("sort") sort: String? = "reputation",
        @Query("inname") inname: String? = null,
        @Query("site") site: String? = "stackoverflow",
    ):UserResponse
}