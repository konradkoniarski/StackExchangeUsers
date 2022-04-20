package co.uk.stackexchangeusers.presentation.userdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.uk.stackexchangeusers.domain.interactors.GetUserUseCase
import co.uk.stackexchangeusers.domain.model.User
import co.uk.stackexchangeusers.domain.model.UserResponse
import co.uk.stackexchangeusers.service.SchedulerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import timber.log.Timber

class UserDetailsViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val schedulerService: SchedulerService
) : ViewModel(), KoinComponent {

    val userLiveData = MutableLiveData<User>()
    val errorLiveData = MutableLiveData<Boolean>()

    var accountId: Int = 0

    fun start(accountId:Int) {
        this.accountId = accountId
        loadData()
    }

    fun loadData() {
        errorLiveData.postValue(false)
        CoroutineScope(schedulerService.IO).launch {
            var userResponse: UserResponse? = null
            try {
                val data = getUserUseCase.execute(accountId)
                userResponse = data
            } catch (e: Throwable) {
                Timber.e("Exception ${e.message}")
                withContext(schedulerService.Main) {
                    errorLiveData.value = true
                }
            }
            withContext(schedulerService.Main)
            {
                userResponse?.items?.firstOrNull().let { userLiveData.value = it }
            }
        }
    }

}