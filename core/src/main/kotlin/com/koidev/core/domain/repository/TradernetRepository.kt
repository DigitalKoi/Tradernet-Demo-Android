package com.koidev.core.domain.repository

import com.koidev.core.domain.Quote
import kotlinx.coroutines.flow.Flow

interface TradernetRepository {

    fun subscribeToQuotes(request: String): Flow<List<Quote>>

    fun unsubscribe()
}
