package com.koidev.core.domain.interactor

import com.koidev.core.domain.base.Interactor
import com.koidev.core.domain.repository.TradernetRepository
import javax.inject.Inject

class UnsubscribeQuotesData @Inject constructor(
    private val repository: TradernetRepository
) : Interactor<Unit>() {

    override suspend fun doWork(params: Unit) {
        repository.unsubscribe()
    }
}
