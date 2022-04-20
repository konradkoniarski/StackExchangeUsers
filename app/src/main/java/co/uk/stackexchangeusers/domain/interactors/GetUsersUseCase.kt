package co.uk.stackexchangeusers.domain.interactors

import co.uk.stackexchangeusers.domain.model.UserResponse
import co.uk.stackexchangeusers.repository.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetUsersUseCase: KoinComponent {

    private val repository: UserRepository by inject()

    @Throws(Throwable::class)
    suspend fun execute():UserResponse{
        return repository.getUsers()
    }
}