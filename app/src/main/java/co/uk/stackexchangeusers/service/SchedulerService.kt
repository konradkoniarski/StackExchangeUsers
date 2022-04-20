package co.uk.stackexchangeusers.service

import kotlin.coroutines.CoroutineContext

interface SchedulerService {
    val Main: CoroutineContext
    val IO: CoroutineContext
}