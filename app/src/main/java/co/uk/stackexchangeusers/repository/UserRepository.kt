package co.uk.stackexchangeusers.repository

import co.uk.stackexchangeusers.domain.model.UserResponse
import co.uk.stackexchangeusers.service.NetworkService
import org.koin.core.component.KoinComponent

class UserRepository(private val networkService: NetworkService): KoinComponent {

    suspend fun getUsers():UserResponse
    {
        return networkService.getUserApi().getUsers()
    }
}