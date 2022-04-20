package co.uk.stackexchangeusers

import co.uk.stackexchangeusers.service.SchedulerService
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import kotlin.coroutines.CoroutineContext

class TestSchedulerService : SchedulerService, KoinComponent {
    override val Main: CoroutineContext = Dispatchers.Unconfined
    override val IO: CoroutineContext = Dispatchers.Unconfined
}