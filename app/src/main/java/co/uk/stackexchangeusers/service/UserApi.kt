package co.uk.stackexchangeusers.service

import co.uk.stackexchangeusers.domain.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    companion object {
        const val SITE = "stackoverflow"
        const val ORDER = "desc"
        const val SORT = "reputation"
    }

    @GET("users")
    suspend fun getUsers(
        @Query("order") order: String? = ORDER,
        @Query("sort") sort: String? = SORT,
        @Query("inname") inname: String? = null,
        @Query("site") site: String? = SITE,
    ):UserResponse

    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") id: Int,
        @Query("site") site: String? = SITE,
    ):UserResponse
}