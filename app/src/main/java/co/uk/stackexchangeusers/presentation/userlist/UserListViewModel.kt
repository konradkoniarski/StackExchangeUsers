package co.uk.stackexchangeusers.presentation.userlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.uk.stackexchangeusers.domain.interactors.GetUsersUseCase
import co.uk.stackexchangeusers.domain.model.UserResponse
import co.uk.stackexchangeusers.service.SchedulerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import timber.log.Timber

class UserListViewModel(
    private val getUserListUseCase: GetUsersUseCase,
    private val schedulerService: SchedulerService
) : ViewModel(), KoinComponent {

    val userListLiveData = MutableLiveData<UserResponse>()
    val errorLiveData = MutableLiveData<Boolean>()

    fun start() {
        loadData()
    }

    fun loadData() {
        errorLiveData.postValue(false)
        CoroutineScope(schedulerService.IO).launch {
            var userResponse: UserResponse? = null
            try {
                val data = getUserListUseCase.execute()
                userResponse = data
            } catch (e: Throwable) {
                Timber.e("Exception ${e.message}")
                withContext(schedulerService.Main) {
                    errorLiveData.value = true
                }
            }
            withContext(schedulerService.Main)
            {
                userResponse.let { userListLiveData.value = it }
            }
        }
    }
}