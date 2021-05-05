package com.koidev.core.data.repository

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.koidev.core.data.mapper.toDomain
import com.koidev.core.data.network.response.ErrorResponse
import com.koidev.core.data.network.response.ErrorResponse.ErrorTypeResponse.*
import com.koidev.core.data.network.response.QuoteResponse
import com.koidev.core.data.network.socket.WebSocketClient
import com.koidev.core.domain.model.PcpUpdateStatus
import com.koidev.core.domain.model.Quote
import com.koidev.core.domain.repository.TradernetRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.collections.HashMap

class DefaultTradernetRepository @Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    private val webSocketClient: WebSocketClient
) : TradernetRepository {

    private val quoteHashMap = HashMap<String, Quote>()

    override suspend fun subscribeToQuotes(request: String): Flow<List<Quote>> = webSocketClient
        .startSocketAndSubscribeToData(request)
        .filterNotNull()
        .onEach { if (it is ErrorResponse && it.type == FAILURE_CONNECTION) throw Exception() }
        .filter { it is QuoteResponse && it.isNotNullableFields() }
        .map { it as QuoteResponse }
        .map { it.toDomain(quoteHashMap[it.ticker]) }
        .onEach { quoteHashMap[it.ticker] = it }
        .map { quoteHashMap.values.toList() }

    override fun unsubscribe() {
        webSocketClient.unsubscribe()
    }

}
