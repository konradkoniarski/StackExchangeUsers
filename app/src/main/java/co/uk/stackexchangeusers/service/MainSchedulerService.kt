package co.uk.stackexchangeusers.service

import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import kotlin.coroutines.CoroutineContext

open class MainSchedulerService : SchedulerService, KoinComponent {
    override val Main: CoroutineContext by lazy { Dispatchers.Main }
    override val IO: CoroutineContext by lazy { Dispatchers.IO }
}