package co.uk.stackexchangeusers.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.uk.stackexchangeusers.TestSchedulerService
import co.uk.stackexchangeusers.domain.interactors.GetUsersUseCase
import co.uk.stackexchangeusers.domain.model.BadgeCounts
import co.uk.stackexchangeusers.domain.model.User
import co.uk.stackexchangeusers.domain.model.UserResponse
import co.uk.stackexchangeusers.presentation.userlist.UserListViewModel
import co.uk.stackexchangeusers.service.SchedulerService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get

@RunWith(JUnit4::class)
class UserListViewModelTest : KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    val appModule = module {
        single { TestSchedulerService() as SchedulerService }
        factory { GetUsersUseCase() }
        factory { UserListViewModel(get(), get()) }
    }

    lateinit var viewModel: UserListViewModel

    val users_empty =
        UserResponse(has_more = false, items = listOf(), quota_max = 0, quota_remaining = 0)

    val users_one = UserResponse(
        has_more = false, items = listOf(
            User(
                1,
                BadgeCounts(1, 2, 3),
                1,
                "Name",
                "Location",
                "Image",
                1,
                2,
                3,
            )
        ), quota_max = 0, quota_remaining = 0
    )

    @Before
    fun before() {
        stopKoin()
        startKoin {
            modules(appModule)
        }
    }

    @Test
    fun whenAppStartedThenRunGetUsersListUseCase() {
        runBlocking {
            val getUsersUseCaseMock = mock<GetUsersUseCase>()
            whenever(getUsersUseCaseMock.execute(null)).thenReturn(users_empty)
            viewModel = UserListViewModel(
                getUsersUseCaseMock,
                get()
            )
            viewModel.start()
            Assert.assertEquals(users_empty, viewModel.userListLiveData.value)
        }
    }

    @Test
    fun whenSearchWithNameThenRunGetUsersListUseCaseWithName() {
        runBlocking {
            val getUsersUseCaseMock = mock<GetUsersUseCase>()
            whenever(getUsersUseCaseMock.execute("any")).thenReturn(users_one)
            viewModel = UserListViewModel(
                getUsersUseCaseMock,
                get()
            )
            viewModel.search("any")
            Assert.assertEquals(users_one, viewModel.userListLiveData.value)
        }
    }

    @Test
    fun whenSearchWithNameFailedThenSetFlat() {
        runBlocking {
            val getUsersUseCaseMock = mock<GetUsersUseCase>()
            whenever(getUsersUseCaseMock.execute("any")).thenThrow(Exception())
            viewModel = UserListViewModel(
                getUsersUseCaseMock,
                get()
            )
            viewModel.search("any")
            Assert.assertEquals(true, viewModel.errorLiveData.value)
        }
    }


}