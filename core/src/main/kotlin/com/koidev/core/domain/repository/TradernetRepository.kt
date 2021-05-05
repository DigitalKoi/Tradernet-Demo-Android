package com.koidev.core.domain.repository

import com.koidev.core.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface TradernetRepository {

    suspend fun subscribeToQuotes(request: String): Flow<List<Quote>>

    fun unsubscribe()
}
