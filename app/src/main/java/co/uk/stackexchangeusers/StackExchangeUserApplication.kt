package co.uk.stackexchangeusers

import co.uk.stackexchangeusers.domain.interactors.GetUsersUseCase
import co.uk.stackexchangeusers.presentation.userlist.UserListViewModel
import co.uk.stackexchangeusers.repository.UserRepository
import co.uk.stackexchangeusers.service.MainSchedulerService
import co.uk.stackexchangeusers.service.NetworkService
import co.uk.stackexchangeusers.service.SchedulerService
import co.uk.stackexchangeusers.service.SettingsService
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class StackExchangeUserApplication: android.app.Application() {

    val appModule = module {
        single { NetworkService(get()) }
        single { SettingsService(get()) }

        single { UserRepository(get()) }

        single { GetUsersUseCase() }

        single { MainSchedulerService() as SchedulerService }

        viewModel { UserListViewModel(get(), get()) }
    }

    override fun onCreate() {
        startKoin {
            androidContext(this@StackExchangeUserApplication)
            modules(appModule)
        }

        Timber.plant(Timber.DebugTree())

        super.onCreate()
    }
}