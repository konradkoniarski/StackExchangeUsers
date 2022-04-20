package co.uk.stackexchangeusers.repository

import co.uk.stackexchangeusers.domain.model.UserResponse
import co.uk.stackexchangeusers.service.NetworkService
import co.uk.stackexchangeusers.service.UserApi
import org.koin.core.component.KoinComponent

class UserRepository(private val networkService: NetworkService): KoinComponent {

    private val apiInstance: UserApi = networkService.getUserApi()

    suspend fun getUsers():UserResponse
    {
        return apiInstance.getUsers()
    }

    suspend fun getUser(id:Int):UserResponse
    {
        return apiInstance.getUser(id)
    }
}